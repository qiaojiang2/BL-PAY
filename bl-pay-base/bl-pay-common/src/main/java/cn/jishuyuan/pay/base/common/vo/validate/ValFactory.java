package cn.jishuyuan.pay.base.common.vo.validate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description 参数验证工具类
 * @encoding UTF-8
 * @date 2017/6/27
 * @time 14:35
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class ValFactory {
    /**
     * 参数注解校验统一验证方法
     *
     * @param object
     * @return
     */
    public static String[] valid(Object object) {
        String[] res = new String[2];
        //获取bean下都有哪些字段
        Field[] field1 = object.getClass().getDeclaredFields();
        Field[] field2 = object.getClass().getSuperclass().getDeclaredFields();
        Field[] fields = new Field[field1.length];
        if (field2 != null && field2.length > 0) {
            fields = new Field[field1.length + field2.length];
            System.arraycopy(field1, 0, fields, 0, field1.length);
            System.arraycopy(field2, 0, fields, field1.length, field2.length);
        } else {
            System.arraycopy(field1, 0, fields, 0, field1.length);
        }
        //循环遍历字段
        for (Field field : fields) {
            try {
                if ("serialVersionUID".equals(field.getName())) {
                    continue;
                }
                String methodName = "get"
                        + field.getName().substring(0, 1).toUpperCase()
                        + field.getName().substring(1);
                Method m = object.getClass().getMethod(methodName, null);
                Object value1 = m.invoke(object, null); // 获取字段的值
                // 获取每个字段都有哪些注解
                Annotation[] annotationsMethos = field.getAnnotations();
                // 遍历注解类
                for (Annotation annotation : annotationsMethos) {
                    // 获取注解对应的字段
                    Method[] mths = annotation.getClass().getDeclaredMethods();
                    if (mths != null && mths.length > 0) {
                        // 获取注解对应的校验类
                        String s = annotation.toString();
                        Object checkobj = Class.forName(
                                s.split("\\$")[0].substring(1)).newInstance();
                        // 遍历获取每个注解类对应的值
                        for (Method mth : mths) {
                            String name = mth.getName();
                            if ("hashCode,equals,toString,annotationType"
                                    .contains(name)) {
                                continue;
                            }
                            Object value2 = mth.invoke(annotation, null);// 获取注解的值

                            HashMap<String, Object> map = new HashMap<String, Object>();
                            map.put(mth.getName(), value2);
                            map.put("val", value1);
                            map.put("obj", object);
                            map.put("fieldname", field.getName());
                            String mthname = "check" + name;// 校验方法名
                            Method ms = checkobj.getClass().getMethod(mthname,
                                    HashMap.class);
                            Object checkflag = ms.invoke(checkobj, map);
                            if (checkflag != null) {
                                String[] result = (String[]) checkflag;
                                res[0] = result[0];
                                res[1] = result[1];
                                return res;
                            }
                        }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                res[0] = "-2";
                res[1] = "请求参数数据校验异常";
                return res;
            }
        }
        return null;
    }
}
