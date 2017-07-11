package cn.jishuyuan.pay.base.common.vo.req;

import cn.jishuyuan.pay.base.common.vo.validate.LenValidators;
import cn.jishuyuan.pay.base.common.utils.BLPayConstant;
import cn.jishuyuan.pay.base.common.vo.validate.DataValidators;
import lombok.Data;

import java.io.Serializable;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description 身份认证接口请求参数
 * @encoding UTF-8
 * @date 2017/6/27
 * @time 11:04
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
@Data
public class IdVerifyReqVo implements Serializable {
    @LenValidators.LenValid(len = {10}, isNotNull = true)
    @DataValidators.DataValid(vals = {BLPayConstant.BIZ_TYPE_CYCLING}, isNotNull = true)
    private final String biz_type;//业务类型
    @LenValidators.LenValid(len = {10}, isNotNull = true)
    @DataValidators.DataValid(vals = {BLPayConstant.TRADE_TYPE_ID_VERIFY}, isNotNull = true)
    private final String trade_type;//交易类型
    @LenValidators.LenValid(len = {10}, isNotNull = true)
    @DataValidators.DataValid(vals = {BLPayConstant.PRODUCT_TYPE_ID_CARD, BLPayConstant.PRODUCT_TYPE_BANK_CARD}, isNotNull = true)
    private final String product_type;//产品类型
    @LenValidators.LenValid(len = {16}, isNotNull = true)
    private final String id_name;//身份证姓名
    @LenValidators.LenValid(len = {18}, isNotNull = true)
    private final String id_number;//身份证号码
    @LenValidators.LenValid(len = {19}, isNotNull = false)
    private final String card_number;//银行卡号
    @LenValidators.LenValid(len = {11}, isNotNull = false)
    private final String phone_number;//手机号码
}
