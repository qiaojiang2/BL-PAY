package java8.part1.chapter03;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author qiao <jiangqiao, jiangqiao@mobike.com>
 * @version v1.0
 * @project bl-pay
 * @Description
 * @encoding UTF-8
 * @date 2017/10/13
 * @time 19:48
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class ExecuteAround {
    public static void main(String args[]) throws IOException {
        System.out.println(processFile());
        System.out.println(processFile((BufferedReader br) -> br.readLine() + br.readLine()));

        forEach(Arrays.asList(1, 2, 3, 4, 5), (Integer i) -> System.out.println(i));

        List<Integer> l = map(Arrays.asList("lambdas", "in", "action"), (String s) -> s.length());
        forEach(l, (Integer i) -> System.out.println("new " + i));

        List<Apple> inventory = Arrays.asList(new Apple(80, "green"),
                new Apple(155, "green"), new Apple(120, "red"));

        //方法引用
        inventory.sort(Comparator.comparing(Apple::getWeight));
        System.out.println(inventory);

        //方法引用
        List<String> str = Arrays.asList("a", "b", "A", "B");
        str.sort(String::compareToIgnoreCase);
        System.out.println(str);

        //构造函数方法引用
        Supplier<Apple> c1 = Apple::new;
        Apple a1 = c1.get();
        a1.setColor("hat");
        System.out.println(a1.getColor());

        BiFunction<Integer, String, Apple> c2 = Apple::new;
        Apple a2 = c2.apply(200, "red");
        System.out.println(a2.getColor());

        List<Integer> weights = Arrays.asList(7, 3, 4, 10);
        List<Apple> apples = map(weights, Apple::new);
        System.out.println(apples);

        //同时构造三个参数
        AppleExtFunction<Integer, String, Integer, Apple> c3 = Apple::new;
        Apple a3 = c3.apply(300, "yellow", 15);
        System.out.println(a3.getColor());

    }

    public static String processFile() throws IOException {
        try (
                BufferedReader br = new BufferedReader(new FileReader("C:\\D\\git\\bl-pay\\bl-pay-base\\bl-pay-example\\src\\test\\java\\java8\\part1\\chapter03\\data.txt"))) {
            return br.readLine();
        }
    }

    public static String processFile(BufferedReaderProcessor p) throws IOException {
        try (
                BufferedReader br = new BufferedReader(new FileReader("C:\\D\\git\\bl-pay\\bl-pay-base\\bl-pay-example\\src\\test\\java\\java8\\part1\\chapter03\\data.txt"))) {
            return p.process(br);
        }
    }

    public static <T> void forEach(List<T> list, Consumer<T> c) {
        for (T i : list) {
            c.accept(i);
        }
    }

    public static <T, R> List<R> map(List<T> list, Function<T, R> f) {
        List<R> result = new ArrayList<>();
        for (T s : list) {
            result.add(f.apply(s));
        }
        return result;
    }
}

class Apple {
    private int weight = 0;
    private String color = "";

    private int price;

    public Apple(int weight, String color) {
        this.weight = weight;
        this.color = color;
    }

    public Apple(int weight, String color, int price) {
        this.weight = weight;
        this.color = color;
        this.price = price;
    }

    public Apple() {

    }

    public Apple(int weight) {
        this.weight = weight;

    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String toString() {
        return "Apple{" +
                "color='" + color + '\'' +
                ", weight=" + weight +
                '}';
    }

}
