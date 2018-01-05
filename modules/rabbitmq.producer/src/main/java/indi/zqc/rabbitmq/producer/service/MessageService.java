package indi.zqc.rabbitmq.producer.service;

/**
 * Title : service.java
 * Package : indi.zqc.rabbitmq.producer
 * Description : 消息服务
 * Create on : 2018/1/5 14:12
 *
 * @author Zhu.Qianchang
 * @version v1.0.0
 */
public interface MessageService {

    /**
     * 发送消息（ExchangeType=direct）
     * @param routingKey
     * @param data
     * @param queueName
     */
    void sendDirectMessage(String routingKey, Object data,String queueName);

    /**
     * 发送消息（ExchangeType=fanout）
     * @param data
     * @param queueName
     */
    void sendFanoutMessage(Object data,String queueName);

    /**
     * 发送消息（ExchangeType=topic）
     * @param routingKey
     * @param data
     * @param queueNames
     */
    void sendTopicMessage(String routingKey, Object data, String... queueNames);
}
