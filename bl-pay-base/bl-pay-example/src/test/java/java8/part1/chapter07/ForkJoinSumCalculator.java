package java8.part1.chapter07;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description
 * @encoding UTF-8
 * @date 2017/10/17
 * @time 13:55
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class ForkJoinSumCalculator extends RecursiveTask<Long> {
    private final long[] numbers; //要求和的数组
    private final int start;  //子任务处理的数组的起始位置
    private final int end;//子任务处理的数组的结束位置

    public static final long THRESHOLD = 10_000;//不再将任务分解为子任务的数组大小

    //公用构造函数用于创建主任务
    public ForkJoinSumCalculator(long[] numbers) {
        this(numbers, 0, numbers.length);
    }

    //私有构造函数用于以递归方式为主任务创建子任务
    private ForkJoinSumCalculator(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        int length = end - start; //覆盖RecursiveTask抽象方法（该任务负责求和的部分的大小）
        if (length <= THRESHOLD) {
            return computeSequentially(); //如果大小小于或等于阀值，顺序计算结果
        }
        //创建一个子任务来为数组的前一半求和
        ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + length / 2);
        leftTask.fork();

        //利用另外一个ForkJoinPool线程异步执行新创建的子任务
        ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start + length / 2, end);

        Long rightResult = rightTask.compute();
        Long leftResult = leftTask.compute();
        return leftResult + rightResult;
    }

    //在子任务不再可分时计算结果的简单算法
    public long computeSequentially() {
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += numbers[i];
        }
        return sum;
    }

    public static long forkJoinSum(long n) {
        long[] numbers = LongStream.rangeClosed(1, n).toArray();
        ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
        return new ForkJoinPool().invoke(task);
    }

    public static void main(String args[]) {
        System.out.println(forkJoinSum(10000));
    }
}
