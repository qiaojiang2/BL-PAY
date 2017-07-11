package cn.jishuyuan.pay.base.common.redisson;

import cn.jishuyuan.pay.base.common.utils.StringUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.Strings;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.config.AbstractFactoryBean;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description Redisson/Redis客户端封装工厂类
 * @encoding UTF-8
 * @date 2017/7/4
 * @time 14:19
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
@Slf4j
public class RedissonClientFactory extends AbstractFactoryBean<RedissonClient> {
    @Setter
    private RedissonProperties redissonProperties;

    @Override
    protected RedissonClient createInstance() throws Exception {
        final Config config = new Config();
        if (this.redissonProperties != null) {
            switch (this.redissonProperties.getMode()) {
                case SINGLE:
                    final SingleServerConfig singleServerConfig = config.useSingleServer();
                    singleServerConfig.setAddress(StringUtils.isNullOrEmpty(this.redissonProperties.getServers()) ? RedissonProperties.DEFAULT_SERVERS : this.redissonProperties.getServers());
                    if (this.redissonProperties.getConnectionMinimumIdleSize() != null) {
                        singleServerConfig.setConnectionMinimumIdleSize(this.redissonProperties.getConnectionMinimumIdleSize());
                    }
                    if (this.redissonProperties.getSubscriptionConnectionMinimumIdleSize() != null) {
                        singleServerConfig.setSubscriptionConnectionMinimumIdleSize(this.redissonProperties.getSubscriptionConnectionMinimumIdleSize());
                    }
                    if (this.redissonProperties.getSubscriptionConnectionPoolSize() != null) {
                        singleServerConfig.setSubscriptionConnectionPoolSize(this.redissonProperties.getSubscriptionConnectionPoolSize());
                    }
                    if (this.redissonProperties.getDnsMonitoring() != null) {
                        singleServerConfig.setDnsMonitoring(this.redissonProperties.getDnsMonitoring());
                    }
                    if (this.redissonProperties.getDnsMonitoringInterval() != null) {
                        singleServerConfig.setDnsMonitoringInterval(this.redissonProperties.getDnsMonitoringInterval());
                    }
                    if (this.redissonProperties.getConnectionPoolSize() != null) {
                        singleServerConfig.setConnectionPoolSize(this.redissonProperties.getConnectionPoolSize());
                    }
                    if (this.redissonProperties.getTimeout() != null) {
                        singleServerConfig.setTimeout(this.redissonProperties.getTimeout());
                    }

                    if (this.redissonProperties.getConnectTimeout() != null) {
                        singleServerConfig.setConnectTimeout(this.redissonProperties.getConnectTimeout());
                    }

                    if (this.redissonProperties.getIdleConnectionTimeout() != null) {
                        singleServerConfig.setIdleConnectionTimeout(this.redissonProperties.getIdleConnectionTimeout());
                    }

                    if (this.redissonProperties.getReconnectionTimeout() != null) {
                        singleServerConfig.setReconnectionTimeout(this.redissonProperties.getReconnectionTimeout());
                    }

                    if (this.redissonProperties.getPingTimeout() != null) {
                        singleServerConfig.setPingTimeout(this.redissonProperties.getPingTimeout());
                    }

                    if (this.redissonProperties.getRetryAttempts() != null) {
                        singleServerConfig.setRetryAttempts(this.redissonProperties.getRetryAttempts());
                    }

                    if (this.redissonProperties.getRetryInterval() != null) {
                        singleServerConfig.setRetryInterval(this.redissonProperties.getRetryInterval());
                    }

                    if (this.redissonProperties.getSubscriptionsPerConnection() != null) {
                        singleServerConfig.setSubscriptionsPerConnection(this.redissonProperties.getSubscriptionsPerConnection());
                    }

                    if (!StringUtils.isNullOrEmpty(this.redissonProperties.getPassword())) {
                        singleServerConfig.setPassword(this.redissonProperties.getPassword());
                    }

                    if (!StringUtils.isNullOrEmpty(this.redissonProperties.getClientName())) {
                        singleServerConfig.setClientName(this.redissonProperties.getClientName());
                    }
                    log.debug("redis single server config:{}", singleServerConfig);
                    break;
                case CLUSTER:
                    final ClusterServersConfig clusterServersConfig = config.useClusterServers();
                    final String[] serverArray = this.redissonProperties.getServers().split(";");
                    clusterServersConfig.addNodeAddress(serverArray);
                    if (this.redissonProperties.getScanInterval() != null) {
                        clusterServersConfig.setScanInterval(this.redissonProperties.getScanInterval());
                    }
                    if (this.redissonProperties.getClusterSlaveSubscriptionConnectionMinimumIdleSize() != null) {
                        clusterServersConfig.setSlaveSubscriptionConnectionMinimumIdleSize(this.redissonProperties.getClusterSlaveSubscriptionConnectionMinimumIdleSize());
                    }

                    if (this.redissonProperties.getClusterSlaveSubscriptionConnectionPoolSize() != null) {
                        clusterServersConfig.setSlaveSubscriptionConnectionPoolSize(this.redissonProperties.getClusterSlaveSubscriptionConnectionPoolSize());
                    }

                    if (this.redissonProperties.getClusterSlaveConnectionMinimumIdleSize() != null) {
                        clusterServersConfig.setSlaveConnectionMinimumIdleSize(this.redissonProperties.getClusterSlaveConnectionMinimumIdleSize());
                    }

                    if (this.redissonProperties.getClusterSlaveConnectionPoolSize() != null) {
                        clusterServersConfig.setSlaveConnectionPoolSize(this.redissonProperties.getClusterSlaveConnectionPoolSize());
                    }

                    if (this.redissonProperties.getClusterMasterConnectionMinimumIdleSize() != null) {
                        clusterServersConfig.setMasterConnectionMinimumIdleSize(this.redissonProperties.getClusterMasterConnectionMinimumIdleSize());
                    }

                    if (this.redissonProperties.getClusterMasterConnectionPoolSize() != null) {
                        clusterServersConfig.setMasterConnectionPoolSize(this.redissonProperties.getClusterMasterConnectionPoolSize());
                    }

                    if (this.redissonProperties.getTimeout() != null) {
                        clusterServersConfig.setTimeout(this.redissonProperties.getTimeout());
                    }

                    if (this.redissonProperties.getConnectTimeout() != null) {
                        clusterServersConfig.setConnectTimeout(this.redissonProperties.getConnectTimeout());
                    }

                    if (this.redissonProperties.getIdleConnectionTimeout() != null) {
                        clusterServersConfig.setIdleConnectionTimeout(this.redissonProperties.getIdleConnectionTimeout());
                    }

                    if (this.redissonProperties.getReconnectionTimeout() != null) {
                        clusterServersConfig.setReconnectionTimeout(this.redissonProperties.getReconnectionTimeout());
                    }

                    if (this.redissonProperties.getPingTimeout() != null) {
                        clusterServersConfig.setPingTimeout(this.redissonProperties.getPingTimeout());
                    }

                    if (this.redissonProperties.getRetryAttempts() != null) {
                        clusterServersConfig.setRetryAttempts(this.redissonProperties.getRetryAttempts());
                    }

                    if (this.redissonProperties.getRetryInterval() != null) {
                        clusterServersConfig.setRetryInterval(this.redissonProperties.getRetryInterval());
                    }

                    if (this.redissonProperties.getSubscriptionsPerConnection() != null) {
                        clusterServersConfig.setSubscriptionsPerConnection(this.redissonProperties.getSubscriptionsPerConnection());
                    }

                    if (!StringUtils.isNullOrEmpty(this.redissonProperties.getPassword())) {
                        clusterServersConfig.setPassword(this.redissonProperties.getPassword());
                    }

                    if (!StringUtils.isNullOrEmpty(this.redissonProperties.getClientName())) {
                        clusterServersConfig.setClientName(this.redissonProperties.getClientName());
                    }

                    log.debug("redis cluster config:{}", clusterServersConfig);
                    break;
            }
        }
        if (this.redissonProperties.getNettyThreads() != null) {
            config.setNettyThreads(this.redissonProperties.getNettyThreads());
        }
        if (this.redissonProperties.getRedissonReferenceEnabled() != null) {
            config.setRedissonReferenceEnabled(this.redissonProperties.getRedissonReferenceEnabled());
        }

        if (this.redissonProperties.getUseLinuxNativeEpoll() != null) {
            config.setUseLinuxNativeEpoll(this.redissonProperties.getUseLinuxNativeEpoll());
        }

        if (this.redissonProperties.getThreads() != null) {
            config.setThreads(this.redissonProperties.getThreads());
        }
        return Redisson.create(config);
    }

    @Override
    public Class<?> getObjectType() {
        return RedissonClient.class;
    }

    @Override
    protected void destroyInstance(final RedissonClient instance) throws Exception {
        super.destroyInstance(instance);
        if (instance != null) {
            try {
                instance.shutdown();
            } catch (final Exception e) {
                log.debug("shutdown RedissonClient error:{}", e.getMessage(), e);
            }
        }
    }
}
