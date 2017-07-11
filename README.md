【版本1.0.0】

项目结构说明：

bl-pay

    |-bl-pay-dispathcer：对外接口暴露层及服务编排调度层；
    |-bl-pay-trad：交易逻辑控制模块，用于交易相关的业务逻辑处理及控制；
    |-bl-pay-risk        ：交易风险控制模块，用于识别风险交易并进行相应风控措施；
    |-bl-pay-route       ：交易渠道路由选择，用于支付通道的控制与选择；
    |-bl-pay-channel     ：交易渠道通讯交互处理模块；
    |-bl-pay-notify      ：交易结果商户通知通讯处理模块；
    |-bl-pay-scheduler   ：支付中心任务调度程序；
    |-bl-pay-base        ：支付中心基础代码模块；
          |- bl-pay-common ：基础工具jar包
          |- bl-pay-model  ：持久层域模型统一jar包
          
软件运行环境说明：

    jdk:1.8+
    tomcat: 8+
    mysql:5.6+
    redis:3.0+
    saturn