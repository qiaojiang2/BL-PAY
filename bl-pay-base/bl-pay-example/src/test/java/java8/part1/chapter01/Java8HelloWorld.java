package java8.part1.chapter01;

import java.util.function.IntToLongFunction;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description
 * @encoding UTF-8
 * @date 2017/9/23
 * @time 11:16
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class Java8HelloWorld {
    public static void main(String args[]) {
        Runnable helloWorld = A::helloWorld;
        Runnable h = helloWorld::run;
        h.run();

        IntToLongFunction intToDoubleFunction = (int x) -> x + 1;
        System.out.println(intToDoubleFunction.applyAsLong(2));
    }

}

class A {
    public static void helloWorld() {
        System.out.println("hello world");
    }
}
