import org.omg.CORBA.TIMEOUT;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.*;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description
 * @encoding UTF-8
 * @date 2017/7/16
 * @time 16:55
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class VolatileUseScene02 {
    static Map configOptions = Collections.synchronizedMap(new HashMap<String, String>());
    char[] configText;
    static boolean initalized = false;

    public static void initConfig() {
        System.out.println("初始化完毕");
        processConfigOptions();
        initalized = true;
    }

    public static void processConfigOptions() {
        System.out.println("配置可用");
        configOptions = new HashMap();
        configOptions.put("aa", "bb");
    }

    public static void main(String args[]) throws Exception {

        Thread[] threads = new Thread[2];
        threads[0] = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!initalized) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (configOptions.get("aa") != null) {
                    System.out.println("配置可用");
                } else {
                    System.out.println("配置不可用");
                }
            }
        }, "线程A");

        threads[1] = new Thread(new Runnable() {
            @Override
            public void run() {
                initConfig();
            }
        }, "线程B");
        threads[0].start();
        threads[1].start();

    }
}
