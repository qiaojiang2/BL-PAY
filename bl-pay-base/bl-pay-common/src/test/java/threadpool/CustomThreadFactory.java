package threadpool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description 线程工厂
 * @encoding UTF-8
 * @date 2017/7/19
 * @time 9:51
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class CustomThreadFactory implements ThreadFactory {
    private AtomicInteger count = new AtomicInteger(0);

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        String threadName = CustomThreadPoolExecutor.class.getSimpleName() + count.addAndGet(1);
        System.out.println(threadName);
        t.setName(threadName);
        return t;
    }
}
