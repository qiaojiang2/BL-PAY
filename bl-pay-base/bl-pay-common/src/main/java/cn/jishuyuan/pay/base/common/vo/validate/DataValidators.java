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
 * @Description 数据合法性校验器
 * @encoding UTF-8
 * @date 2017/6/27
 * @time 15:11
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class DataValidators extends Validators {
    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DataValid {
        boolean isNotNull() default false; // 非空校验

        String[] vals() default {};// 指定值校验
    }

    // 校验是否为空
    public String[] checkisNotNull(HashMap<String, Object> map) {
        String[] res = new String[2];
        boolean flag = (Boolean) map.get("isNotNull");
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

    // 校验字段值是否为指定值或处于指定值范围内
    public String[] checkvals(HashMap<String, Object> map) {
        String[] res = new String[2];
        StringBuffer sb = new StringBuffer();
        String[] vals = (String[]) map.get("vals");// 指定值
        Object obj1 = map.get("val");// 数据
        Object fieldname = map.get("fieldname");
        if (obj1 != null && vals != null && vals.length > 0
                && obj1.toString().trim().length() > 0) {
            String str = obj1.toString();
            for (int i = 0; i < vals.length; i++) {
                if (i < vals.length - 1) {
                    sb.append(vals[i] + "|");
                } else {
                    sb.append(vals[i]);
                }
                if (vals[i].equals(str)) {
                    return null;
                }
            }
            res[0] = "-1";
            res[1] = "请求参数不合法,字段[" + fieldname.toString() + "]不是指定值[" + sb.toString()
                    + "]";
            return res;
        }
        return null;
    }
}
