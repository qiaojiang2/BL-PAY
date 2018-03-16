package java8.part1.chapter04;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description
 * @encoding UTF-8
 * @date 2017/10/16
 * @time 15:54
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class StreamApiDemo02 {
    public static void main(String args[]) {
        //流只能被消费一次
        List<String> title = Arrays.asList("Java8", "In", "Action");
        Stream<String> s = title.stream();
        s.forEach(System.out::println);


    }
}
