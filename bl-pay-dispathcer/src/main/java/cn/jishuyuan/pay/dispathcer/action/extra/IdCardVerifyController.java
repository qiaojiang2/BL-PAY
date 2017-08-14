package cn.jishuyuan.pay.dispathcer.action.extra;

import cn.jishuyuan.pay.dispathcer.action.SuperHead;
import cn.jishuyuan.pay.base.common.vo.BaseRequest;
import cn.jishuyuan.pay.base.common.vo.BaseResponse;
import cn.jishuyuan.pay.base.common.vo.req.IdVerifyReqVo;
import cn.jishuyuan.pay.base.common.vo.res.IdVerifyResVo;
import cn.jishuyuan.pay.base.model.PayAppKey;
import cn.jishuyuan.pay.base.model.PayAppKeyExample;
import cn.jishuyuan.pay.dispathcer.action.verify.RequestParmsVerify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import cn.jishuyuan.pay.base.mappers.PayAppKeyMapper;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description 身份认证接口（根据通道情况支持二要素、三要素、四要素身份认证）
 * @encoding UTF-8
 * @date 2017/6/26
 * @time 16:05
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
@RestController
@RequestMapping("/v1")
public class IdCardVerifyController extends SuperHead {
    /**
     * 绑定验证对象方法
     *
     * @param binder
     */
    @InitBinder
    protected void initDataBinder(DataBinder binder) {
        binder.addValidators(new RequestParmsVerify(IdVerifyReqVo.class));
    }

    @RequestMapping(value = "/wr/extra/idVerify", method = RequestMethod.POST)
    public BaseResponse idVerify(@RequestBody @Validated BaseRequest baseRequest, BindingResult result, final HttpServletRequest request) {
        //数据校验逻辑处理
        if (result.hasErrors()) {
            ObjectError error = result.getAllErrors().get(0);
            System.out.print(error.getCode().toString() + "，" + error.getDefaultMessage());
            return BaseResponse.failedResponse(Integer.valueOf(error.getCode()), error.getDefaultMessage()).build(baseRequest);
        }
        //todo 数据处理逻辑
        String hello = "hello";
        //baseRequest.getData();
        IdVerifyResVo vo = IdVerifyResVo.builder().biz_type("cycling").build();
        return BaseResponse.successResponse("认证成功").setObj(vo).build(baseRequest);
    }
}
