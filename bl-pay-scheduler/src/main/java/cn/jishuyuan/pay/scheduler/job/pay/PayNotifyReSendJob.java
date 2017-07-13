package cn.jishuyuan.pay.scheduler.job.pay;

import com.vip.saturn.job.AbstractSaturnJavaJob;
import com.vip.saturn.job.SaturnJobExecutionContext;
import com.vip.saturn.job.SaturnJobReturn;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description 支付结果商户通知重发任务
 * @encoding UTF-8
 * @date 2017/7/13
 * @time 10:58
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class PayNotifyReSendJob extends AbstractSaturnJavaJob {
    /**
     * 支付结果通知重发任务入口方法
     *
     * @param jobName
     * @param shardItem
     * @param shardParam
     * @param shardingContext
     * @return
     * @throws InterruptedException
     */
    @Override
    public SaturnJobReturn handleJavaJob(String jobName, Integer shardItem, String shardParam, SaturnJobExecutionContext shardingContext) throws InterruptedException {
        System.out.println("支付结果通知重发.running handleJavaJob:" + jobName + "; " + shardItem + ";" + shardParam);
        return new SaturnJobReturn("我是分片" + shardItem + "的处理结果");
    }
}
