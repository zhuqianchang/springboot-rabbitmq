package indi.zqc.rabbitmq.base.model;

import indi.zqc.rabbitmq.base.constants.ExchangeType;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Title : Producer.java
 * Package : indi.zqc.rabbitmq.base.model
 * Description : 生产者
 * Create on : 2018/1/5 10:00
 *
 * @author Zhu.Qianchang
 * @version v1.0.0
 */
public class MessageSender<T> {

    private ConnectionFactory connectionFactory;

    private RabbitTemplate rabbitTemplate;

    private RabbitAdmin rabbitAdmin;

    private String exchangeName;

    private String routingKey;

    private List<String> queueNames = new ArrayList<>();

    private ExchangeType exchangeType;

    public MessageSender(ConnectionFactory connectionFactory, String exchangeName, String routingKey, List<String> queueNames, ExchangeType exchangeType) {
        this.connectionFactory = connectionFactory;
        this.exchangeName = exchangeName;
        this.routingKey = routingKey;
        this.queueNames = queueNames;
        this.exchangeType = exchangeType;
        this.rabbitTemplate = new RabbitTemplate(connectionFactory);
        this.rabbitAdmin = new RabbitAdmin(connectionFactory);
    }

    /**
     * 创建路由和队列之间的关系
     */
    public void build() {
        if (queueNames != null) {
            for (String queueName : queueNames) {
                Queue queue = new Queue(queueName);
                queue.setAdminsThatShouldDeclare(rabbitAdmin);
                rabbitAdmin.declareQueue(queue);
                switch (exchangeType) {
                    case DIRECT:
                        DirectExchange directExchange = new DirectExchange(exchangeName);
                        rabbitAdmin.declareExchange(directExchange);
                        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(directExchange).with(routingKey));
                        break;
                    case TOPIC:
                        TopicExchange topicExchange = new TopicExchange(exchangeName);
                        rabbitAdmin.declareExchange(topicExchange);
                        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(topicExchange).with(routingKey));
                        break;
                    case FANOUT:
                        FanoutExchange fanoutExchange = new FanoutExchange(exchangeName);
                        rabbitAdmin.declareExchange(fanoutExchange);
                        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(fanoutExchange));
                        break;
                }
            }
        }
    }

    /**
     * 发送消息
     * @param routingKey
     * @param message
     */
    public void send(String routingKey, T message) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
    }

    /**
     * 发送消息
     * @param message
     */
    public void send(T message) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
    }

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public List<String> getQueueNames() {
        return queueNames;
    }

    public void setQueueNames(List<String> queueNames) {
        this.queueNames = queueNames;
    }

    public void addQueueNames(String... queueNames) {
        for (String queueName : queueNames) {
            this.queueNames.add(queueName);
        }
    }

    public ExchangeType getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(ExchangeType exchangeType) {
        this.exchangeType = exchangeType;
    }
}
