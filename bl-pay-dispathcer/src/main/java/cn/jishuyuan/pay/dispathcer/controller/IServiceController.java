package cn.jishuyuan.pay.dispathcer.controller;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description 流程编排层暴露接口
 * @encoding UTF-8
 * @date 2017/6/26
 * @time 15:08
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public interface IServiceController<T, V> {
    /**
     * 流程编排调用处理方法
     *
     * @param v
     * @return
     * @throws Exception
     */
    public T execute(V v) throws Exception;
}
