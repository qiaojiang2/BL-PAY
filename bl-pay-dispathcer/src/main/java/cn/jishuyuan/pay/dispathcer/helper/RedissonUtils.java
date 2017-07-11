package cn.jishuyuan.pay.dispathcer.helper;

import cn.jishuyuan.pay.base.model.PayAppKey;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description Redisson客户端操作工具类
 * @encoding UTF-8
 * @date 2017/7/7
 * @time 14:49
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
@Slf4j
@Component
public class RedissonUtils {
    private static volatile RedissonUtils redissonUtils = null;
    private RedissonClient redissonClient = null;

    @Autowired
    public void setRedissonClient(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
        redissonUtils = this;
    }

    /**
     * 获取RedissonUtils工具类实例
     *
     * @return
     */
    public static RedissonUtils getInstance() {
        return redissonUtils;
    }

    /**
     * 获取redissonClient
     *
     * @return
     */
    public RedissonClient getRedissonClient() {
        return redissonClient;
    }


    /**
     * 根据key值获取对象信息
     *
     * @param key
     * @return
     */
    public Object getValueObject(final String key) {
        return execute(key, new RedisRBucketAction<Object>() {
            @Override
            Object execute(RBucket<Object> rBucket) {
                if (rBucket.isExists()) {
                    Object value = rBucket.get();
                    return value;
                } else {
                    return null;
                }
            }
        });
    }

    /**
     * 设置对象至Redis服务器方法
     *
     * @param key
     * @param value
     * @param second
     * @return
     */
    public boolean setValueObject(final String key, final Object value, long second) {
        Object obj = execute(key,
                new RedisRBucketAction<Object>() {
                    @Override
                    String execute(RBucket<Object> rBucket) {
                        Object temp = rBucket.get();
                        if (!rBucket.isExists() || temp == null) {
                            if (second < 0) {
                                rBucket.set(value);
                            } else {
                                rBucket.set(value, second, TimeUnit.SECONDS);
                            }
                        } else {
                            return "false";
                        }
                        return "true";
                    }
                });
        return Boolean.valueOf(obj.toString());
    }

    /**
     * 内部执行方法
     *
     * @param key
     * @param action
     * @param <T>
     * @return
     */

    private <T> T execute(String key, RedisRBucketAction<T> action) {
        RedissonClient client = getRedissonClient();
        RBucket<T> rBucket = client.getBucket(key);
        return action.execute(rBucket);
    }

    /**
     * 内部代理类
     *
     * @param <T>
     */
    private abstract class RedisRBucketAction<T> {
        abstract T execute(RBucket<T> rBucket);
    }
}
