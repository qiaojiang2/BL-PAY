import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description
 * @encoding UTF-8
 * @date 2017/7/21
 * @time 15:13
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class ReentrantLockTest2 {
    /**
     * 大体上，当你使用sychronized关键字的时候，需要写的代码量更少，并且用户错误出现的可能性也会更低，通常只有再解决特殊问题时
     * 才使用显示的Lock对象。
     * 例如，用synchronized关键字不能尝试着获取锁且最终获取锁会失败，或者尝试获取锁一段时间，然后放弃它。
     */
    private ReentrantLock lock = new ReentrantLock();

    public void untimed() {
        boolean captured = lock.tryLock();
        try {
            System.out.println("tryLock():" + captured);
        } finally {
            if (captured) {
                lock.unlock();
            }
        }
    }

    public void timed() {
        boolean captured = false;
        try {
            captured = lock.tryLock(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("tryLock(2,TimeUnit.SECONDS)：" + captured);
        } finally {
            if (captured) {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        final ReentrantLockTest2 al = new ReentrantLockTest2();
        al.untimed();
        al.timed();
        new Thread() {
            {
                setDaemon(true);//设置后台线程
            }

            public void run() {
                al.lock.lock();
                System.out.println("acquired");
            }
        }.start();
        Thread.yield();//给第二个任务一个机会
        al.untimed();  //false
        al.timed();//false
    }
}
