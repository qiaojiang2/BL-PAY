package java8.part1.chapter02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description
 * @encoding UTF-8
 * @date 2017/9/26
 * @time 15:13
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class FilteringApples {
    //筛选绿苹果的方法
    public static List<Apple> filterGreenApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<Apple>();
        for (Apple apple : inventory) {
            if ("green".equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }

    //抽象化1-将颜色作为参数
    public static List<Apple> filterApplesByColor(List<Apple> inventory, String color) {
        List<Apple> result = new ArrayList<Apple>();
        for (Apple apple : inventory) {
            if (apple.getColor().equals(color)) {
                result.add(apple);
            }
        }
        return result;
    }

    //变化-应对重量的筛选方法
    public static List<Apple> filterApplesByWeight(List<Apple> inventory, int weight) {
        List<Apple> result = new ArrayList<Apple>();
        for (Apple apple : inventory) {
            if (apple.getWeight() > weight) {
                result.add(apple);
            }
        }
        return result;
    }

    //第三次尝试-对你能想到的每个属性做筛选
    public static List<Apple> filterApples(List<Apple> inventory, String color, int weight, boolean flag) {
        List<Apple> result = new ArrayList<Apple>();
        for (Apple apple : inventory) {
            if ((flag && apple.getColor().equals(color)) || !flag && apple.getWeight() > weight) {
                result.add(apple);
            }
        }
        return result;
    }

    //行为参数化
    public interface ApplePredicate {
        boolean test(Apple apple);
    }

    class AppleRedAndHeavyPredicate implements ApplePredicate {

        @Override
        public boolean test(Apple apple) {
            return "red".equals(apple.getColor()) && apple.getWeight() > 150;
        }
    }

    //第四次尝试
    public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p) {
        List<Apple> result = new ArrayList<Apple>();
        for (Apple apple : inventory) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    //第七次尝试->进一步抽象化
    public interface Predicate<T> {
        boolean test(T t);
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> result = new ArrayList<T>();
        for (T e : list) {
            if (p.test(e)) {
                result.add(e);
            }
        }
        return result;
    }

    //真实的例子
    public interface Comparator<T> {
        public int compare(T o1, T o2);
    }

    //用Runnable执行代码块
    public interface Runnable {
        public void run();
    }

    public static void main(String args[]) {
        List<Apple> inventory = Arrays.asList(new Apple(80, "green"),
                new Apple(155, "green"), new Apple(120, "red"));

        //第五次尝试-使用匿名类
        List<Apple> redApples = filterApples(inventory, new ApplePredicate() {
            @Override
            public boolean test(Apple apple) {
                return "red".equals(apple.getColor());
            }
        });

        //第六次尝试-使用Lambda表达式
        List<Apple> result = filterApples(inventory, (Apple apple) -> "red".equals(apple.getColor()));

        //第七次尝试
        List<Apple> redApples2 = filter(inventory, (Apple apple) -> "red".equals(apple.getColor()));

        System.out.println("redApples2->" + redApples2);

        List<Apple> evenNumbers = filter(inventory, (Apple apple) -> apple.getWeight() > 150);
        System.out.println("evenNumbers->" + evenNumbers);

        //真实例子
        inventory.sort(new java.util.Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getWeight().compareTo(o2.getWeight());
            }
        });

        System.out.println("sort1->" + inventory);

        inventory.sort((Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight()));

        System.out.println("sort2->" + inventory);


        Thread t = new Thread(new java.lang.Runnable() {
            @Override
            public void run() {
                System.out.println("Hello World");
            }
        });

        Thread t2 = new Thread(() -> System.out.println("Hello world"));

        t.start();
        t2.start();
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
