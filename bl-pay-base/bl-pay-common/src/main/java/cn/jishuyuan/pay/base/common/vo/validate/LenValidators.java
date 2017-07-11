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
 * @Description 参数长度校验器
 * @encoding UTF-8
 * @date 2017/6/27
 * @time 11:17
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class LenValidators extends Validators {
    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface LenValid {
        boolean isNotNull() default false;//非空校验

        int[] len() default {};//长度校验，定长或指定长度段
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

    /**
     * 校验参数长度是否为指定长度或处于指定范围内
     *
     * @param map
     * @return
     */
    public String[] checklen(HashMap<String, Object> map) {
        String[] res = new String[2];
        Object datalen = map.get("len");//最小长度
        Object obj1 = map.get("val");//数据
        Object fieldname = map.get("fieldname");
        if (obj1 != null && datalen != null && obj1.toString().trim().length() > 0) {
            int[] dlen = (int[]) datalen;
            String str = obj1.toString();
            if (dlen.length == 1) {
                if (str.length() > dlen[0]) {
                    res[0] = "-1";
                    res[1] = "请求参数不合法,字段[" + fieldname.toString() + "]超长";
                    return res;
                }
            } else if (dlen.length == 2) {
                if (!(str.length() >= dlen[0] && str.length() <= dlen[1])) {
                    res[0] = "-1";
                    res[1] = "请求参数不合法,字段[" + fieldname.toString() + "]长度错误";
                    return res;
                }
            }
        }
        return null;
    }
}
