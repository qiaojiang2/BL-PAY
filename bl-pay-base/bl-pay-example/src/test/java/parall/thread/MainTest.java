package parall.thread;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description
 * @encoding UTF-8
 * @date 2017/7/21
 * @time 14:02
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class MainTest {
    public static void main(String args[]) {
        RequestQueue requestQueue = new RequestQueue();
        for (int i = 0; i < 10; i++) {
            new ClientThread(requestQueue, "Alice " + i, 100).start();
        }
        for (int j = 0; j < 10; j++) {
            new ServerThread(requestQueue, "Bobby", 100).start();
        }
    }
}
