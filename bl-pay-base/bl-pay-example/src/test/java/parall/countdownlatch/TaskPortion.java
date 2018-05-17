package parall.countdownlatch;

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
 * @time 13:33
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class TaskPortion implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    //private static Random rand = new Random(47);
    private final CountDownLatch latch;   //必须设置至构造方法中

    public TaskPortion(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            doWork();
            latch.countDown();   //每个线程退出前必须调用countDown方法
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void doWork() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(2000));
        System.out.println(this + "completed");
    }

    public String toString() {
        return String.format("%1$-3d", id);
    }
}
