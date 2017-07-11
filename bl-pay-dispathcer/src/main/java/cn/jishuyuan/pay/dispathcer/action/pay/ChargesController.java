package cn.jishuyuan.pay.dispathcer.action.pay;

import cn.jishuyuan.pay.dispathcer.action.SuperHead;
import cn.jishuyuan.pay.base.common.vo.BaseRequest;
import cn.jishuyuan.pay.base.common.vo.BaseResponse;
import cn.jishuyuan.pay.base.common.vo.req.ChargesReqVo;
import cn.jishuyuan.pay.base.common.vo.req.IdVerifyReqVo;
import cn.jishuyuan.pay.dispathcer.action.verify.RequestParmsVerify;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description 统一支付请求接口
 * @encoding UTF-8
 * @date 2017/6/26
 * @time 15:37
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
@RestController
@RequestMapping("/v1")
public class ChargesController extends SuperHead {
    /**
     * 绑定验证对象方法
     *
     * @param binder
     */
    @InitBinder
    protected void initDataBinder(DataBinder binder) {
        binder.addValidators(new RequestParmsVerify(ChargesReqVo.class));
    }

    @RequestMapping(value = "/wr/pay/charges", method = RequestMethod.POST)
    public BaseResponse idVerify(@RequestBody @Validated BaseRequest baseRequest, BindingResult result, final HttpServletRequest request) {
        //数据校验逻辑处理
        if (result.hasErrors()) {
            ObjectError error = result.getAllErrors().get(0);
            System.out.print(error.getCode().toString() + "，" + error.getDefaultMessage());
            return new BaseResponse.Builder(baseRequest).failedResponse(Integer.valueOf(error.getCode()), error.getDefaultMessage());
        }
        return null;
    }
}
