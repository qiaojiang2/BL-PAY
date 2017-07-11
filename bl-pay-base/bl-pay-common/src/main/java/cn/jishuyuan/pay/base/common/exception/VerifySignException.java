package cn.jishuyuan.pay.base.common.exception;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description 签名错误异常
 * @encoding UTF-8
 * @date 2017/7/3
 * @time 19:17
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class VerifySignException extends Exception {
    public VerifySignException() {
        super();
    }

    public VerifySignException(String message, Throwable cause) {
        super(message, cause);
    }

    public VerifySignException(String message) {
        super(message);
    }
}
