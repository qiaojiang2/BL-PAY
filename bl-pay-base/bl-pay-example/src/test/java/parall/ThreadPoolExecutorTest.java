package parall;

import parall.threadpool.CustomThreadPoolExecutor;

import java.util.concurrent.ExecutorService;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description
 * @encoding UTF-8
 * @date 2017/7/18
 * @time 17:38
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class ThreadPoolExecutorTest {
    public static void main(String[] args) {
        CustomThreadPoolExecutor exec = new CustomThreadPoolExecutor();
        exec.init();//初始化

        ExecutorService pool = exec.getCustomThreadPoolExecutor();
        for (int i = 1; i < 100; i++) {
            System.out.println("提交第" + i + "个任务!");
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("running=========");
                }
            });
            //模拟正常处理
            /*try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        exec.destory();
    }
}
