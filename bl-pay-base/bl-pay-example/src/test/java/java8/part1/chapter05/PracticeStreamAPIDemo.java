package java8.part1.chapter05;

import java.util.*;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * @author qiao <jiangqiao, jiangqiao@mobike.com>
 * @version v1.0
 * @project bl-pay
 * @Description
 * @encoding UTF-8
 * @date 2017/10/17
 * @time 10:45
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class PracticeStreamAPIDemo {
    public static void main(String args[]) {
        //交易员
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        //交易记录
        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 500),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        //找出2011年发生的所有交易，并按交易额排序(从低到高)
        transactions.stream().filter(d -> d.getYear() == 2011).sorted(Comparator.comparing(Transaction::getValue)).collect(toList()).stream().map(l -> l.getYear() + "|" + l.getValue()).forEach(System.out::println);

        //交易员都在那些不同的城市工作过
        transactions.stream().map(Transaction::getTrader).map(Trader::getCity).distinct().forEach(System.out::println);
        transactions.stream().map(l -> l.getTrader().getCity()).distinct().forEach(System.out::println);
        Set<String> cities =
                transactions.stream()
                        .map(transaction -> transaction.getTrader().getCity())
                        .collect(toSet());

        //查找所有来自于剑桥的交易员，并按姓名排序
        transactions.stream().map(Transaction::getTrader).filter(d -> d.getCity() == "Cambridge").map(Trader::getName).sorted().distinct().forEach(System.out::println);

        //返回所有交易员的姓名字符串，按字母顺序排序
        transactions.stream().map(Transaction::getTrader).map(l -> l.getName()).map(ll -> ll.split("")).flatMap(Arrays::stream).sorted().forEach(System.out::println);
        String traderStr =
                transactions.stream()
                        .map(transaction -> transaction.getTrader().getName())
                        .distinct()
                        .sorted()
                        .reduce("", (n1, n2) -> n1 + n2);

        String traderStr2 =
                transactions.stream()
                        .map(transaction -> transaction.getTrader().getName())
                        .distinct()
                        .sorted()
                        .collect(joining());


        //有没有交易员是在米兰工作的
        boolean b = transactions.stream().map(Transaction::getTrader).anyMatch(l -> l.getCity() == "Milan");
        System.out.println("is milan->" + b);

        //打印生活在剑桥的交易员的所有交易额
        transactions.stream().filter(l -> l.getTrader().getCity() == "Cambridge").map(Transaction::getValue).forEach(System.out::println);

        //所有交易中，最高的交易额是多少
        Optional<Integer> maxTrade = transactions.stream().map(Transaction::getValue).reduce(Integer::max);
        System.out.println("最高交易额->" + maxTrade.get());

        //找到交易额最小的交易
        transactions.stream().map(Transaction::getValue).reduce(Integer::min);
        Optional<Transaction> smallestTransaction =
                transactions.stream()
                        .reduce((t1, t2) ->
                                t1.getValue() < t2.getValue() ? t1 : t2);

        Optional<Transaction> smallestTransaction2 =
                transactions.stream()
                        .min(Comparator.comparing(Transaction::getValue));
    }

}

//交易员类
class Trader {
    private final String name;
    private final String city;

    public Trader(String n, String c) {
        this.name = n;
        this.city = c;
    }

    public String getName() {
        return this.name;
    }

    public String getCity() {
        return this.city;
    }

    public String toString() {
        return "Trader:" + this.name + " in " + this.city;
    }
}

//交易记录类
class Transaction {
    private final Trader trader;
    private final int year;
    private final int value;

    public Transaction(Trader trader, int year, int value) {
        this.trader = trader;
        this.year = year;
        this.value = value;
    }

    public Trader getTrader() {
        return this.trader;
    }

    public int getYear() {
        return this.year;
    }

    public int getValue() {
        return this.value;
    }

    public String toString() {
        return "{" + this.trader + ", " +
                "year: " + this.year + ", " +
                "value:" + this.value + "}";
    }
}