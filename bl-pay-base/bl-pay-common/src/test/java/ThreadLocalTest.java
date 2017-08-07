import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description
 * @encoding UTF-8
 * @date 2017/7/18
 * @time 15:36
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class ThreadLocalTest {
    private static final AtomicInteger uniqueId = new AtomicInteger(0);//原子数值类型

    //线程局部变量
    private static final ThreadLocal<Integer> uniqueNum = new ThreadLocal<Integer>() {
        public Integer initialValue() {
            return uniqueId.incrementAndGet();
        }
    };

    public static void main(String[] args) {
        Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            String name = "Thread-" + i;
            threads[i] = new Thread(name) {
                public void run() {
                    System.out.println(Thread.currentThread().getName() + ":" + uniqueNum.get());
                }
            };
            threads[i].start();
        }
    }

}
