package threadpool2;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description 阻塞线程池
 * @encoding UTF-8
 * @date 2017/7/19
 * @time 10:23
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class CustomBlockPoolExecutor {
    private ThreadPoolExecutor pool = null; //线程池方法

    //但提交任务被拒绝时，进入拒绝机制，在实现的拒绝方法中，我们把任务重新用阻塞提交方法put提交，实现“阻塞提交任务功能”，防止队列过大，发生OOM
    public void init() {
        pool = new ThreadPoolExecutor(1, 3, 30, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(5), new CustomBlockThreadFactory(), new CustomBlockRejectedExecutionHandler());
    }

    /**
     * 线程池销毁方法
     */
    public void destory() {
        if (pool != null) {
            pool.shutdownNow();
        }
    }

    public ExecutorService getCustomBlockThreadPoolExecutor() {
        return this.pool;
    }
}

