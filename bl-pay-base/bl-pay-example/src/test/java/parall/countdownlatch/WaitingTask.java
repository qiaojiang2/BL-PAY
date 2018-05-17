package parall.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description
 * @encoding UTF-8
 * @date 2017/7/19
 * @time 13:46
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class WaitingTask implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private final CountDownLatch latch;

    public WaitingTask(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            latch.await();
            System.out.println("开始执行等待任务...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        return String.format("WatingTask %1$-3d", id);
    }
}
