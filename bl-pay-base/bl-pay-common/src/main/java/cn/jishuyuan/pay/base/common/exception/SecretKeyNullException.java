package cn.jishuyuan.pay.base.common.exception;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description 应用密钥信息空指针异常
 * @encoding UTF-8
 * @date 2017/7/3
 * @time 19:49
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class SecretKeyNullException extends Exception {
    public SecretKeyNullException() {
        super();
    }

    public SecretKeyNullException(String message) {
        super(message);
    }

    public SecretKeyNullException(String message, Throwable cause) {
        super(message, cause);
    }
}
