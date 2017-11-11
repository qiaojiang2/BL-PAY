package java8.part1.chapter01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description
 * @encoding UTF-8
 * @date 2017/9/23
 * @time 11:39
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class FilterAppleDemo {
    public static void main(String args[]) {
        List<Apple> inventory = Arrays.asList(new Apple(80, "green"),
                new Apple(155, "green"), new Apple(120, "red"));

        //方法引用语法（::）
        List<Apple> greenApples = filterApples(inventory, FilterAppleDemo::isGreenApple);
        System.out.println(greenApples);
        List<Apple> heavyApples = filterApples(inventory, FilterAppleDemo::isHeavyApple);
        System.out.println(heavyApples);

        //Lambada表达式
        long startTime1 = System.currentTimeMillis();
        List<Apple> greenApples2 = filterApples(inventory, (Apple a) -> "green".equals(a.getColor()));
        long endTime1 = System.currentTimeMillis();
        System.out.println("greenApples2->" + greenApples + "|耗时" + (endTime1 - startTime1) + "毫秒");

        List<Apple> heavyApples2 = filterApples(inventory, (Apple a) -> a.getWeight() > 150);
        System.out.println("heavyApples2->" + heavyApples2);

        //组合判断
        List<Apple> weirdApples = filterApples(inventory, (Apple a) -> a.getWeight() < 80 || "brown".equals(a.getColor()));
        System.out.println("weirdApples->" + weirdApples);


        //StreamApi初使
        List<Apple> heaveyApples4 = inventory.stream().filter((Apple a) -> a.getWeight() > 150).collect(toList());
        System.out.println("heaveyApples4->" + heaveyApples4);

        //并行流
        long startTime2 = System.currentTimeMillis();
        List<Apple> greenApples5 = inventory.parallelStream().filter((Apple a) -> "green".equals(a.getColor())).collect(toList());
        long endTime2 = System.currentTimeMillis();
        System.out.println("greenApples5" + greenApples5 + "|耗时" + (endTime2 - startTime2) + "毫秒");

        //第三章方法引用示例
        inventory.sort(Comparator.comparing(Apple::getWeight));
    }

    //筛选绿苹果
    public static List<Apple> filterGreenApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if ("green".equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }

    //筛选重量
    public static List<Apple> filterHeavyApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (apple.getWeight() > 150) {
                result.add(apple);
            }
        }
        return result;
    }

    //判断是否绿苹果
    public static boolean isGreenApple(Apple apple) {
        return "green".equals(apple.getColor());
    }

    public static boolean isHeavyApple(Apple apple) {
        return apple.getWeight() > 150;
    }

    public interface Predicate<T> {
        boolean test(T t);
    }

    public static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> p) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }
}

 class Apple {
    private int weight = 0;
    private String color = "";

    public Apple(int weight, String color) {
        this.weight = weight;
        this.color = color;
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