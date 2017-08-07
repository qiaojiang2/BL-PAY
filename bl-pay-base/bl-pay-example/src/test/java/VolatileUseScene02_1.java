/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description
 * @encoding UTF-8
 * @date 2017/7/16
 * @time 19:32
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class VolatileUseScene02_1 {
    /**
     * 这是一个验证结果的变量
     */
    private volatile static int a = 0;
    /**
     * 这是一个标志位
     */
    private volatile static boolean flag = false;

    public static void main(String[] args) throws InterruptedException {
        //由于多线程情况下未必会试出重排序的结论,所以多试一些次
        for (int i = 0; i < 1000; i++) {
            ThreadA threadA = new ThreadA();
            ThreadB threadB = new ThreadB();
            threadA.start();
            threadB.start();
            //这里等待线程结束后,重置共享变量,以使验证结果的工作变得简单些.
            threadA.join();
            threadB.join();
            a = 0;
            flag = false;
        }
    }

    static class ThreadA extends Thread {
        public void run() {
            a = 1;
            flag = true;
        }
    }

    static class ThreadB extends Thread {
        public void run() {
            if (flag) {
                a = a * 1;
            }
            if (a == 0) {
                System.out.println("ha,a==0");
            }
        }
    }
}

