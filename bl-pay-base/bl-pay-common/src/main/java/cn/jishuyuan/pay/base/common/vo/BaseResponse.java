package cn.jishuyuan.pay.base.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description 响应参数构建基类
 * @encoding UTF-8
 * @date 2017/6/27
 * @time 10:15
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
@Data
public class BaseResponse implements Serializable {
    private int code;//响应码
    private String msg;//响应信息
    private String app_id;//应用ID
    private Object result;//响应结果
    private String version;//接口版本号
    private String sign_info;//签名信息

    public BaseResponse(int code, String msg, String app_id, Object result, String version, String sign_info) {
        this.code = code;
        this.msg = msg;
        this.app_id = app_id;
        this.result = result;
        this.version = version;
        this.sign_info = sign_info;
    }

    //构建失败响应报文
    public static BaseResponse.Builder failedResponse(int errorCode, String errorMsg) {
        //todo-响应报文数字签名
        return new BaseResponse.Builder(errorCode, errorMsg);
    }

    //构建成功响应报文
    public static BaseResponse.Builder successResponse(String message) {
        //todo-响应报文数字签名
        return new BaseResponse.Builder(0, message);
    }

    //响应参数构建内部类
    public static final class Builder {
        private static final Map<Object, Object> NULL_OBJ = new HashMap();//空对象
        private int code;//响应码
        private String msg;//响应信息
        private String app_id;//应用ID
        private String version;//接口版本号
        private String sign_info;//签名信息
        private Object result;//响应结果

        public Builder(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public BaseResponse.Builder setObj(Object result) {
            this.result = result;
            return this;
        }

        public BaseResponse build(BaseRequest requestVo) {
            return new BaseResponse(code, msg, requestVo.getApp_id(), this.result == null ? NULL_OBJ : this.result, requestVo.getVersion(), "");
        }
    }
}
