package java8.part1.chapter03;

/**
 * @author qiao <jiangqiao, jiangqiao@mobike.com>
 * @version v1.0
 * @project bl-pay
 * @Description
 * @encoding UTF-8
 * @date 2017/10/14
 * @time 1:19
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
@FunctionalInterface
public interface AppleExtFunction<T, U, V, R> {
    R apply(T t, U u, V v);
}
