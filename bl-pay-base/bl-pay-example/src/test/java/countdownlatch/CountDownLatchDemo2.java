package countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description
 * @encoding UTF-8
 * @date 2017/7/19
 * @time 14:41
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class CountDownLatchDemo2 {
    /**
     * 模拟100米赛跑，10名选手已准备就绪，只等裁判一声令下。当所有人都到达终点时，比赛结束
     */
    public static void main(String[] args) throws InterruptedException {
        //开始的倒数锁
        final CountDownLatch begin = new CountDownLatch(1);
        //结束的倒数锁
        final CountDownLatch end = new CountDownLatch(10);

        //10名选手
        for (int index = 0; index < 10; index++) {
            new Thread(new Player(begin, end), "player" + index).start();
        }
        System.out.println("Game Start");
        //begin减1，计数变为0，开始游戏
        begin.countDown();
        end.await();
        System.out.println("Game Over");
    }
}
