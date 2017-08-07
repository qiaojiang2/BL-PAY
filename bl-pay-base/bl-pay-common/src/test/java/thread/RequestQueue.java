package thread;

import java.util.LinkedList;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description
 * @encoding UTF-8
 * @date 2017/7/21
 * @time 13:47
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class RequestQueue {
    private final LinkedList queue = new LinkedList<>();

    public synchronized Request getRequest() {
        while (queue.size() <= 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return (Request) queue.removeFirst();
    }

    public synchronized void putRequest(Request request) {
        queue.addLast(request);
        notifyAll();//唤醒所有线程
    }
}
