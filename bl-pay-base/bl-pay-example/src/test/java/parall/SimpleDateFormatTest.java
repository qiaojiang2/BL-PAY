package parall;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description
 * @encoding UTF-8
 * @date 2017/7/19
 * @time 18:41
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class SimpleDateFormatTest {
    //private static SimpleDateFormat dateDf = new SimpleDateFormat("yyyy-MM-dd");//线程安全问题

    //线程局部变量
    private static final ThreadLocal<SimpleDateFormat> dateDf = new ThreadLocal<SimpleDateFormat>() {
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    public static void main(String[] args) {
        ExecutorService tmpool = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 100; i++) {
            tmpool.execute(new Runnable() {
                @Override
                public void run() {
                    //synchronized (dateDf) { //加锁
                    try {
                        System.out.println(dateDf.get().parse("2017-07-20"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                //}
            });
        }
        tmpool.shutdown();
    }
}
