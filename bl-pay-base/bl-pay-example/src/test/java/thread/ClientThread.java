package thread;

import com.sun.corba.se.spi.legacy.interceptor.RequestInfoExt;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description
 * @encoding UTF-8
 * @date 2017/7/21
 * @time 13:51
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class ClientThread extends Thread {
    private RequestQueue requestQueue;

    public ClientThread(RequestQueue requestQueue, String name, long seed) {
        super(name);
        this.requestQueue = requestQueue;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            Request request = new Request(this.getName());
            requestQueue.putRequest(request);
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
