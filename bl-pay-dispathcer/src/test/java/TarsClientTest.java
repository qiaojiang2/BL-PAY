import client.testapp.HelloPrx;
import client.testapp.HelloPrxCallback;
import com.qq.tars.client.Communicator;
import com.qq.tars.client.CommunicatorConfig;
import com.qq.tars.client.CommunicatorFactory;
import com.qq.tars.common.util.Config;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * @author qiao <jiangqiao, jiangqiao@coolqi.com>
 * @version v1.0
 * @project coolqi-pay
 * @Description
 * @encoding UTF-8
 * @date 2017/6/22
 * @time 16:34
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class TarsClientTest {
    @Test
    public void tarsClientTest() throws IOException {
        CommunicatorConfig cfg = new CommunicatorConfig();
        //构造通讯器
        Communicator communicator = CommunicatorFactory.getInstance().getCommunicator(cfg);
        //直接寻址方式
        HelloPrx proxy = communicator.stringToProxy(HelloPrx.class, "TestApp.HelloServer.HelloObj@tcp -h 192.168.43.175 -p 20001");
        //主控中心寻址方式
        //HelloPrx proxy = communicator.stringToProxy(HelloPrx.class, "TestApp.HelloServer.HelloObj");
        //同步调用
        String ret = proxy.hello(1000, "Hello World~tars");
        System.out.println(ret);
        //单向调用
        proxy.async_hello(null, 1000, "Hello World");
        //异步调用
        proxy.async_hello(new HelloPrxCallback() {
            @Override
            public void callback_expired() {
            }
            @Override
            public void callback_exception(Throwable ex) {
            }
            @Override
            public void callback_hello(String ret) {
                System.out.println(ret);
            }
        }, 1000, "Hello World");
    }
}
