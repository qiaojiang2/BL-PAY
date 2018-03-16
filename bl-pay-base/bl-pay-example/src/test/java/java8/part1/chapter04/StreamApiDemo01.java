package java8.part1.chapter04;

import java.util.*;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description
 * @encoding UTF-8
 * @date 2017/10/16
 * @time 14:59
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class StreamApiDemo01 {
    public static void main(String args[]) {
        List<Dish> menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH));

        //JAVA7
        List<Dish> lowCaloricDishes = new ArrayList();
        for (Dish d : menu) {
            if (d.getCalories() < 400) {
                lowCaloricDishes.add(d);
            }
        }
        Collections.sort(lowCaloricDishes, new Comparator<Dish>() {
            @Override
            public int compare(Dish o1, Dish o2) {
                return Integer.compare(o1.getCalories(), o2.getCalories());
            }
        });
        List<String> lowCarloricDishesName = new ArrayList<>();
        for (Dish d : lowCaloricDishes) {
            lowCarloricDishesName.add(d.getName());
        }
        System.out.println(lowCarloricDishesName.toString());

        //Java8
        long time1 = System.currentTimeMillis();
        List<String> lowCarloricDishesName2 = new ArrayList<>();

        lowCarloricDishesName2 = menu.stream().filter(l -> l.getCalories() < 400).sorted(Comparator.comparing(Dish::getCalories)).map(Dish::getName).collect(toList());
        long time2 = System.currentTimeMillis();
        System.out.println(lowCarloricDishesName2.toString() + ";耗时" + (time2 - time1) + "");

        //Java8并行流
        long time3 = System.currentTimeMillis();
        List<String> lowCarloricDishesName3 = new ArrayList<>();

        lowCarloricDishesName3 = menu.parallelStream().filter(l -> l.getCalories() < 400).sorted(Comparator.comparing(Dish::getCalories)).map(Dish::getName).collect(toList());
        long time4 = System.currentTimeMillis();
        System.out.println(lowCarloricDishesName3.toString() + ";耗时" + (time4 - time3) + "");

        //JAVA8分组
        //List<String> lowCarloricDishesName4 = new ArrayList<>();
        //lowCarloricDishesName4 = menu.parallelStream().collect(groupingBy(Dish::getType));

        //体验流的好处的代码
        List<String> lowCarloricDishesName5 = new ArrayList<>();
        lowCarloricDishesName5 = menu.stream().filter(o -> o.getCalories() > 300).map(Dish::getName).limit(2).collect(toList());
        System.out.println(lowCarloricDishesName5.toString());

        //内部迭代&&外部迭代
        //外部迭代(for-each 循环外部迭代)
        List<String> names = new ArrayList<>();
        for (Dish d : menu) {
            names.add(d.getName());
        }
        System.out.println("for-each 外部迭代->" + names.toString());

        //外部迭代（背后的迭代器做迭代）
        List<String> names2 = new ArrayList<>();
        Iterator<Dish> iterator = menu.iterator(); //显示迭代
        while (iterator.hasNext()) {
            Dish d = iterator.next();
            names2.add(d.getName());
        }
        System.out.println("迭代器 外部迭代->" + names2.toString());

        //内部迭代
        List<String> names3 = menu.stream().map(Dish::getName).collect(toList());
        System.out.println("外部迭代 ->" + names3.toString());

        //流操作，中间操作示例
        List<String> names5 = menu.stream().filter(d -> {
            System.out.println("filtering " + d.getName());
            return d.getCalories() > 300;
        }).map(d -> {
            System.out.println("mapping " + d.getName());
            return d.getName();
        }).limit(2).collect(toList());
        System.out.println(names5.toString());

        //终端操作
        menu.stream().forEach(System.out::println);
    }


}

class Dish {
    private final String name;
    private final boolean vegetarian;
    private final int calories;
    private final Type type;

    public Dish(String name, boolean vegetarian, int calories, Enum type) {
        this.name = name;
        this.vegetarian = vegetarian;
        this.calories = calories;
        this.type = (Type) type;
    }

    public String getName() {
        return name;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public int getCalories() {
        return calories;
    }

    public Type getType() {
        return type;
    }

    public enum Type {MEAT, FISH, OTHER}

}
