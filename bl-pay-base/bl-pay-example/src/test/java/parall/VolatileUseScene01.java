package parall;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description
 * @encoding UTF-8
 * @date 2017/7/16
 * @time 14:14
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class VolatileUseScene01 {
    public static volatile boolean shutdownRequested;

    public static void shutdown() {
        shutdownRequested = true;
    }

    public static AtomicInteger count = new AtomicInteger(0);

    public static void doWork() {
        while (!shutdownRequested) {
            count.getAndIncrement();
            System.out.println(Thread.currentThread().getName() + "执行dowork操作！");
        }
    }

    public static void main(String args[]) {
        Thread[] threads = new Thread[3];
        threads[0] = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    doWork();
                }
            }
        }, "线程A");
        threads[1] = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程B执行shutdown操作");
                shutdown();
            }
        }, "线程B");
        threads[2] = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    doWork();
                }
            }
        }, "线程C");
        threads[0].start();
        threads[1].start();
        threads[2].start();
        while (Thread.activeCount() > 1) {
            Thread.yield();
        }
        System.out.println("共执行：" + count.get());
    }
}
