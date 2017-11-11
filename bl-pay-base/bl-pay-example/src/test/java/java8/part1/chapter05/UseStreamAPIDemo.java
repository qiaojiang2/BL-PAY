package java8.part1.chapter05;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author qiao <jiangqiao, jiangqiao@mobike.com>
 * @version v1.0
 * @project bl-pay
 * @Description
 * @encoding UTF-8
 * @date 2017/10/16
 * @time 19:55
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class UseStreamAPIDemo {
    public static void main(String args[]) {
        //筛选Distincet(去重)
        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
        numbers.stream().filter(l -> l % 2 == 0).distinct().forEach(System.out::println);


        //跳过元素
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
        List<Dish> dishes = menu.stream()
                .filter(d -> d.getCalories() > 300)
                .skip(2).limit(2).collect(toList());
        dishes.stream().map(Dish::getCalories).forEach(System.out::println);

        //筛选前两个荤菜
        menu.stream()
                .filter(d -> d.getType() == Dish.Type.MEAT)
                .limit(2)
                .collect(toList()).stream().map(Dish::getName).forEach(System.out::println);

        //给流中每个元素应用函数
        List<String> words = Arrays.asList("Java 8", "Lambdas", "In", "Action");
        words.stream()
                .map(String::length)
                .collect(toList()).stream().forEach(System.out::println);

        //菜名的长度
        menu.stream()
                .map(Dish::getName)
                .map(String::length)
                .collect(toList()).stream().forEach(System.out::println);

        //流的扁平化
        String[] words2 = {"Hello", "World"};
        Stream<String> streamOfwords = Arrays.stream(words2);
        Arrays.stream(words2).map(word -> word.split("")).map(Arrays::stream).distinct().collect(toList()).stream().forEach(System.out::println);

        //使用faltMap
        Arrays.stream(words2).map(w -> w.split("")).flatMap(Arrays::stream).distinct().collect(toList()).stream().forEach(System.out::println);

        List<Integer> numbers2 = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> squares = numbers2.stream().map(n -> n * n).collect(toList());
        squares.stream().forEach(System.out::println);

        //数对
        List<Integer> numberList1 = Arrays.asList(1, 2, 3);
        List<Integer> numberList2 = Arrays.asList(3, 4);
        List<int[]> pairs = numberList1.stream().flatMap(i -> numberList2.stream().map(j -> new int[]{i, j})).collect(toList());
        pairs.stream().forEach(l -> Arrays.stream(l).map(i -> i).forEach(System.out::println));


        //查找和匹配
        //检查谓词是否至少匹配一个元素
        if (menu.stream().anyMatch(Dish::isVegetarian)) {
            System.out.println("The menu is (somewhat) vegetarian friendly!!");
        }
        //检查谓词是否匹配所有元素
        boolean isHealthy = menu.stream().allMatch(d -> d.getCalories() < 1000);
        System.out.println(isHealthy);

        //nomatch，VS allMatch
        boolean isHealthy2 = menu.stream().noneMatch(d -> d.getCalories() >= 1000);
        System.out.println(isHealthy2);

        //查找元素
        Optional<Dish> dish = menu.stream().filter(Dish::isVegetarian).findAny();
        System.out.println(dish.isPresent());
        dish.ifPresent(o -> System.out.println(o.getName()));
        System.out.println(dish.get().getName());

        //获取元素值
        menu.stream().filter(Dish::isVegetarian).findAny().ifPresent(d -> System.out.println("new->" + d.getName()));

        //查找第一个元素
        List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);
        Optional<Integer> firstSquareDivisibleByThree = someNumbers.stream().map(x -> x * x).filter(x -> x % 3 == 0).findFirst();
        System.out.println(firstSquareDivisibleByThree.get());

        //归约
        //元素求和
        int sum = numbers.stream().reduce(0, (a, b) -> a + b);
        System.out.println("sum->" + sum);
        int chengji = numbers.stream().reduce(1, (a, b) -> a * b);
        System.out.println("chengji->" + chengji);

        Optional<Integer> reduceNoinit = numbers.stream().reduce((a, b) -> a * b);
        System.out.println(reduceNoinit.get());

        //最大值和最小值
        Optional<Integer> maxValue = numbers.stream().reduce(Integer::max);
        System.out.println("maxValue->" + maxValue.get());

        Optional<Integer> minValue = numbers.stream().reduce(Integer::min);
        System.out.println("minValue->" + minValue.get());

        Optional<Integer> minValue2 = numbers.stream().reduce((x, y) -> x > y ? y : x);
        System.out.println("minValue2->" + minValue2.get());

        //菜单多少菜
        Optional<Integer> menuCount = Optional.ofNullable(menu.stream().map(d -> 1).reduce(0, (a, b) -> a + b));
        System.out.println("menuCount->" + menuCount.get());

        System.out.println("menuCount->" + menu.stream().count());

        //并行求和
        int sum2 = numbers.parallelStream().reduce(0, Integer::sum);
        System.out.println("sum2->" + sum2);
    }
}

class Dish {
    private final String name;
    private final boolean vegetarian;
    private final int calories;
    private final Dish.Type type;

    Dish(String name, boolean vegetarian, int calories, Enum type) {
        this.name = name;
        this.vegetarian = vegetarian;
        this.calories = calories;
        this.type = (Dish.Type) type;
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

    public Dish.Type getType() {
        return type;
    }

    public enum Type {MEAT, FISH, OTHER}
}

