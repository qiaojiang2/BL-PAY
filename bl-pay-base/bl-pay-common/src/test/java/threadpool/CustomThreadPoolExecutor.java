package threadpool;

import java.util.concurrent.*;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description 非阻塞线程池定义
 * @encoding UTF-8
 * @date 2017/7/19
 * @time 9:39
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class CustomThreadPoolExecutor {
    private ThreadPoolExecutor pool = null; //线程池

    /**
     * 初始化方法
     */
    public void init() {
        pool = new ThreadPoolExecutor(10, 30, 30, TimeUnit.MINUTES, new LinkedBlockingDeque<Runnable>(10), new CustomThreadFactory(), new CustomRejectedExecutionHandler());
    }
//
    public void destory() {
        if (pool != null) {
            pool.shutdownNow();//线程池销毁
        }
    }

    public ExecutorService getCustomThreadPoolExecutor() {
        return this.pool;
    }
}
