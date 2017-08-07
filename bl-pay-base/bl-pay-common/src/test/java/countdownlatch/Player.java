package countdownlatch;

import java.sql.Time;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description
 * @encoding UTF-8
 * @date 2017/7/19
 * @time 14:49
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class Player implements Runnable {
    //开始的倒数锁
    private final CountDownLatch begin;
    //结束的倒数锁
    private final CountDownLatch end;

    public Player(CountDownLatch begin, CountDownLatch end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    public void run() {
        try {
            //阻塞等待，如果当前计数器为零，则此方法立即返回
            //begin.await(100, TimeUnit.SECONDS);
            begin.await();
            Thread.sleep(ThreadLocalRandom.current().nextInt(10000));
            System.out.println(Thread.currentThread().getName() + " arrived");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //每个选手到达终点，调用countDown()方法
            end.countDown();
        }

    }
}
