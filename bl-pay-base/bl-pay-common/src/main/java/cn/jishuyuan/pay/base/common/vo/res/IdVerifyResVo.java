package cn.jishuyuan.pay.base.common.vo.res;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description 身份认证接口响应参数
 * @encoding UTF-8
 * @date 2017/6/27
 * @time 11:10
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
@Data
@Builder
public class IdVerifyResVo implements Serializable {
    private final String biz_type;//业务类型
    private final String trade_type;//交易类型
    private final String product_type;//产品类型


}
