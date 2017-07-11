package cn.jishuyuan.pay.base.common.utils;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description 字符串工具类
 * @encoding UTF-8
 * @date 2017/7/4
 * @time 15:57
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class StringUtils {
    /**
     * 字符串对象不为空判断方法
     *
     * @param str
     * @return
     */
    public static boolean isNullOrEmpty(String str) {
        return (str == null) || str.isEmpty();
    }
}
