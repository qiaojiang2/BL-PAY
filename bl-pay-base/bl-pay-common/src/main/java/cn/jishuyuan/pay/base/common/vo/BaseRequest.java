package cn.jishuyuan.pay.base.common.vo;

import cn.jishuyuan.pay.base.common.vo.validate.LenValidators;
import cn.jishuyuan.pay.base.common.vo.validate.DataValidators;
import cn.jishuyuan.pay.base.common.vo.validate.IsNullValidators;
import lombok.Data;

import java.io.Serializable;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description 请求参数基类
 * @encoding UTF-8
 * @date 2017/6/27
 * @time 10:13
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
@Data
public class BaseRequest implements Serializable {
    @LenValidators.LenValid(len = {16}, isNotNull = true)
    private String app_id;//应用ID
    @IsNullValidators.isNullValid(isNotNull = true)
    private Object data;//请求数据域
    @DataValidators.DataValid(vals = {DataValidators.VERSION}, isNotNull = true)
    private String version;//接口版本号
    @LenValidators.LenValid(len = {256}, isNotNull = true)
    private String sign_info;//rsa签名信息

}
