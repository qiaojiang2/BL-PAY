package parall;

import parall.threadpool2.CustomBlockPoolExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description
 * @encoding UTF-8
 * @date 2017/7/19
 * @time 10:42
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class ThreadPoolExecutorTest2 {
    public static void main(String[] args) {
        CustomBlockPoolExecutor exec = new CustomBlockPoolExecutor();
        exec.init();//初始化

        ExecutorService pool = exec.getCustomBlockThreadPoolExecutor();
        for (int i = 0; i < 100; i++) {
            System.out.println("提交第" + i + "个任务");
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(">>>task is running......");
                        TimeUnit.SECONDS.sleep(10);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
