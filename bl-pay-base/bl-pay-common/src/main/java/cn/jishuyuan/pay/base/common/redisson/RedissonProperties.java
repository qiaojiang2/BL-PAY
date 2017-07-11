package cn.jishuyuan.pay.base.common.redisson;

import cn.jishuyuan.pay.base.common.utils.StringUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description Redisson/Redis配置属性类
 * @encoding UTF-8
 * @date 2017/7/4
 * @time 15:25
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
@Data
public class RedissonProperties {
    public static final String DEFAULT_SERVERS = "127.0.0.1:6379";//默认redis地址
    /**
     * Redis服务地址
     */
    @Value("${redisson.servers:}")
    private String servers;

    /**
     * 集群状态扫描间隔时间，单位是毫秒
     */
    @Value("${redisson.cluster.scanInterval:}")
    private Integer scanInterval;

    /**
     * Redis 每个'slave'节点最小连接数
     */
    @Value("${redisson.cluster.slaveSubscriptionConnectionMinimumIdleSize:}")
    private Integer clusterSlaveSubscriptionConnectionMinimumIdleSize;

    /**
     * Redis 每个'slave'节点连接池大小
     */
    @Value("${redisson.cluster.slaveSubscriptionConnectionPoolSize:}")
    private Integer clusterSlaveSubscriptionConnectionPoolSize;

    /**
     * Redis 'slave' node minimum idle connection amount for each slave node
     */
    @Value("${redisson.cluster.slaveConnectionMinimumIdleSize:}")
    private Integer clusterSlaveConnectionMinimumIdleSize;

    /**
     *
     */
    @Value("${redisson.cluster.slaveConnectionPoolSize:}")
    private Integer clusterSlaveConnectionPoolSize;

    /**
     * Redis 'master' node minimum idle connection amount for <b>each</b> slave node
     */
    @Value("${redisson.cluster.masterConnectionMinimumIdleSize:}")
    private Integer clusterMasterConnectionMinimumIdleSize;

    /**
     * Redis 'master' node maximum connection pool size
     */
    @Value("${redisson.cluster.masterConnectionPoolSize:}")
    private Integer clusterMasterConnectionPoolSize;

    /**
     * Timeout during connecting to any Redis server.
     * Value in milliseconds.
     */
    @Value("${redisson.connectTimeout:}")
    private Integer connectTimeout;

    /**
     * Timeout during connecting to any Redis server.
     * Value in milliseconds.
     */
    @Value("${redisson.idleConnectionTimeout:}")
    private Integer idleConnectionTimeout;

    /**
     * Reconnection attempt timeout to Redis
     */
    @Value("${redisson.reconnectionTimeout:}")
    private Integer reconnectionTimeout;

    @Value("${redisson.timeout:}")
    private Integer timeout;
    @Value("${redisson.pingTimeout:}")
    private Integer pingTimeout;
    @Value("${redisson.retryAttempts:}")
    private Integer retryAttempts;
    @Value("${redisson.retryInterval:}")
    private Integer retryInterval;
    @Value("${redisson.subscriptionsPerConnection:}")
    private Integer subscriptionsPerConnection;
    @Value("${redisson.password:}")
    private String password;
    @Value("${redisson.clientName:}")
    private String clientName;

    @Value("${redisson.single.connectionPoolSize:}")
    private Integer connectionPoolSize;

    @Value("${redisson.single.subscriptionConnectionPoolSize:}")
    private Integer subscriptionConnectionPoolSize;

    @Value("${redisson.single.subscriptionConnectionMinimumIdleSize:}")
    private Integer subscriptionConnectionMinimumIdleSize;

    @Value("${redisson.single.connectionMinimumIdleSize:}")
    private Integer connectionMinimumIdleSize;

    @Value("${redisson.single.dnsMonitoring:}")
    private Boolean dnsMonitoring;

    @Value("${redisson.single.dnsMonitoringInterval:}")
    private Long dnsMonitoringInterval;

    @Value("${redisson.nettyThreads:}")
    private Integer nettyThreads;

    @Value("${redisson.threads:}")
    private Integer threads;

    @Value("${redisson.useLinuxNativeEpoll:}")
    private Boolean useLinuxNativeEpoll;

    @Value("${redisson.redissonReferenceEnabled:}")
    private Boolean redissonReferenceEnabled;

    @Value("${redisson.mode:SINGLE}")
    private String mode;

    @Value("${redisson.configLocation:}")
    private String configLocation;

    @Value("${redisson.configCacheLocation:}")
    private String configCacheLocation;

    public EnumMode getMode() {
        return StringUtils.isNullOrEmpty(this.mode) ? EnumMode.SINGLE : EnumMode.valueOf(this.mode);
    }

    enum EnumMode {
        CLUSTER, SINGLE
    }
}
