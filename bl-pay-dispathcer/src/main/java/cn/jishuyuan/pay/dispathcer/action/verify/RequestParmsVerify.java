package cn.jishuyuan.pay.dispathcer.action.verify;

import cn.jishuyuan.pay.base.common.utils.*;
import cn.jishuyuan.pay.dispathcer.helper.RedissonUtils;
import cn.jishuyuan.pay.base.common.exception.SecretKeyNullException;
import cn.jishuyuan.pay.base.common.exception.VerifySignException;
import cn.jishuyuan.pay.base.common.vo.BaseRequest;
import cn.jishuyuan.pay.base.common.vo.validate.ValFactory;
import cn.jishuyuan.pay.base.mappers.PayAppKeyMapper;
import cn.jishuyuan.pay.base.model.PayAppKey;
import cn.jishuyuan.pay.base.model.PayAppKeyExample;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.Map;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description 请求参数统一校验处理类
 * @encoding UTF-8
 * @date 2017/6/26
 * @time 15:16
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
@Slf4j
public class RequestParmsVerify implements Validator {
    private Class classType;//请求参数域数据对象类型

    public RequestParmsVerify(Class classType) {
        this.classType = classType;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    @Override
    public void validate(Object o, Errors errors) {
        try {
            //基础参数验证
            BaseRequest vo = (BaseRequest) o;
            String[] error = ValFactory.valid(vo);
            if (error != null) {
                errors.rejectValue(null, error[0], null, error[1]);
                return;
            }
            PayAppKey payAppKey = (PayAppKey) RedissonUtils.getInstance().getValueObject(RedisKeyConstant.PREFIX_PAY_APP_KEY_ + vo.getApp_id());
            if (payAppKey == null) {
                //根据app获取从数据库获取应用密钥信息
                PayAppKeyMapper payAppKeyMapper = (PayAppKeyMapper) SpringContextUtil.getBean("payAppKeyMapper");
                PayAppKeyExample payAppKeyExample = new PayAppKeyExample();
                payAppKeyExample.createCriteria().andAppIdEqualTo(vo.getApp_id());
                List<PayAppKey> list = payAppKeyMapper.selectByExample(payAppKeyExample);
                if (list == null || list.size() <= 0) {
                    throw new SecretKeyNullException();
                }
                payAppKey = list.get(0);
                //将查询的应用密钥信息缓存至Redis
                boolean result = RedissonUtils.getInstance().setValueObject(RedisKeyConstant.PREFIX_PAY_APP_KEY_ + vo.getApp_id(), payAppKey, -1);
                log.info("{app_id:" + vo.getApp_id() + "}{key:" + RedisKeyConstant.PREFIX_PAY_APP_KEY_ + vo.getApp_id() + "}{应用信息缓存至Redis->" + (result == true ? "成功" : "失败") + "}");
            }
            //对data域数据进行解密操作
            String decryptedData = AesUtils.decrypt(AesUtils.AES_CBC_PKCS5Padding, vo.getData().toString(), payAppKey.getEncryptKey());
            //对data域解密数据进行数据合法性校验
            Object obj = JsonUtil.jsonStringToObject(decryptedData, classType);
            error = ValFactory.valid(obj);
            if (error != null) {
                errors.rejectValue(null, error[0], null, error[1]);
                return;
            }
            //rsa数字签名验证
            Map<String, ?> result = RSAUtil.paraFilter(JsonUtil.objectToMap(obj));//待验签数据转换为Map
            String waitSignData = RSAUtil.createLinkStr(result);
            boolean verifySignResult = RSAUtil.publicDecryptVerifySign(waitSignData, vo.getSign_info(), payAppKey.getRsaPubKey());
            if (!verifySignResult) {
                throw new VerifySignException();
            }
            log.info("{app_id:" + vo.getApp_id() + "}{签名验证通过，报文解密完成}");
        } catch (VerifySignException e) {
            e.printStackTrace();
            errors.rejectValue(null, "-3", null, "数字签名验证失败，请求不合法");
        } catch (SecretKeyNullException e) {
            e.printStackTrace();
            errors.rejectValue(null, "-4", null, "应用密钥信息不存在或已失效，请联系技术人员");
        } catch (Exception e) {
            e.printStackTrace();
            errors.rejectValue(null, "-2", null, "请求数据校验系统异常");
        }
    }
}
