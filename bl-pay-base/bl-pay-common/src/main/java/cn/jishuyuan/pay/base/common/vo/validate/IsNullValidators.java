package cn.jishuyuan.pay.base.common.vo.validate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description 非空参数验证器
 * @encoding UTF-8
 * @date 2017/6/27
 * @time 11:16
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class IsNullValidators extends Validators {
    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface isNullValid {
        boolean isNotNull() default true; // 非空校验
    }

    /**
     * 校验参数是否为空
     *
     * @param map
     * @return
     */
    public String[] checkisNotNull(HashMap<String, Object> map) {
        String[] res = new String[2];
        boolean flag = (boolean) map.get("isNotNull");
        Object obj = map.get("val");
        Object fieldname = map.get("fieldname");
        if (flag) {
            if (obj == null || obj.toString().trim().length() == 0) {
                res[0] = "-1";
                res[1] = "请求参数不合法,字段[" + fieldname.toString() + "]不能为空";
                return res;
            }
        }
        return null;
    }
}
