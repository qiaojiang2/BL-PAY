package parall.thread;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description
 * @encoding UTF-8
 * @date 2017/7/21
 * @time 13:59
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class ServerThread extends Thread {
    private RequestQueue requestQueue;

    public ServerThread(RequestQueue requestQueue, String name, long seed) {
        super(name);
        this.requestQueue = requestQueue;
    }

    public void run() {
        while (true) {
            Request request = requestQueue.getRequest();
            System.out.println(Thread.currentThread().getName() + " handles" + request.getName()+" 's request");
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
