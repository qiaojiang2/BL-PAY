package java8.part1.chapter05;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description
 * @encoding UTF-8
 * @date 2017/10/17
 * @time 11:53
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class CreateStreamApiDemo {
    public static void main(String args[]) {
        //由值创建流
        Stream<String> stream1 = Stream.of("Java 8", "Lambdas", "In", "Action");
        stream1.map(String::toUpperCase).forEach(System.out::println);

        //创建一个空流
        Stream<String> emptyStream = Stream.empty();

        //由数组创建流
        int[] numbers = {1, 2, 3, 4, 5, 6};
        int sum = Arrays.stream(numbers).sum();
        System.out.println(sum);

        //由文件生成流
        long uniqueWords = 0;
        try (Stream<String> lines =
                     Files.lines(Paths.get("C:\\D\\git\\bl-pay\\bl-pay-base\\bl-pay-example\\src\\test\\java\\java8\\part1\\chapter03\\data.txt"), Charset.defaultCharset())) {
            uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" ")))
                    .distinct()
                    .count();
            System.out.println(uniqueWords);
        } catch (IOException e) {
        }

        //由函数生成流:创建无限流
        //迭代
        Stream.iterate(0, n -> n + 2)
                .limit(10)
                .forEach(System.out::println);
        //菲波那切数列
        System.out.println("斐波那契...");
        Stream.iterate(new int[]{0, 1},
                t -> new int[]{t[1], t[0] + t[1]})
                .limit(10)
                .map(t -> t[0])
                .forEach(System.out::println);
    }
}
