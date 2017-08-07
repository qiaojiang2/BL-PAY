import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description
 * @encoding UTF-8
 * @date 2017/7/16
 * @time 17:13
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class Singleton {
    private volatile static Singleton instance;
    private AtomicInteger count = new AtomicInteger(0);

    public Singleton() {
        count.incrementAndGet();
    }

    public static Singleton getInstance() {
        if (instance == null) {
            if (instance == null) {
                shortWait(100000);
                instance = new Singleton(); //非原子性操作
            }
        }
        return instance;
    }

    public void doWork() {
        System.out.println("working.....");
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[10000];

        threads[0] = new Thread(new Runnable() {
            @Override
            public void run() {
                shortWait(100000);
                try {
                    Singleton.getInstance().doWork();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        threads[1] = new Thread(new Runnable() {
            @Override
            public void run() {
                shortWait(100000);
                try {
                    Singleton.getInstance().doWork();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        threads[0].start();//启动线程
        threads[1].start();
        // System.out.println("线程" + threads[i].getName() + " 启动！");
        /*while (Thread.activeCount() > 1) {
            Thread.yield();
        }*/

        //System.out.println(count);
    }

    public static void shortWait(long interval) {
        long start = System.nanoTime();
        long end;
        do {
            end = System.nanoTime();
        } while (start + interval >= end);
    }
}
