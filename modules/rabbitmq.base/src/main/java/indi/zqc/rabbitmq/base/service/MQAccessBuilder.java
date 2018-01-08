package indi.zqc.rabbitmq.base.service;

import indi.zqc.rabbitmq.base.constants.ExchangeType;
import indi.zqc.rabbitmq.base.model.MessageConsumer;
import indi.zqc.rabbitmq.base.model.MessageSender;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

/**
 * Title : MQAccessBuilder.java
 * Package : indi.zqc.rabbitmq.base.service
 * Description : 消息对象Builder
 * Create on : 2018/1/8 15:22
 *
 * @author Zhu.Qianchang
 * @version v1.0.0
 */
public interface MQAccessBuilder {

    MessageSender buildMessageSender(String queueName);

    MessageSender buildMessageSender(ConnectionFactory connectionFactory, String queueName);

    MessageSender buildMessageSender(String exchangeName, String routingKey, String queueName, ExchangeType exchangeType);

    MessageSender buildMessageSender(ConnectionFactory connectionFactory, String exchangeName, String routingKey, String queueName, ExchangeType exchangeType);

    MessageConsumer buildMessageConsumer(String queueName, MessageListener messageListener);

    MessageConsumer buildMessageConsumer(ConnectionFactory connectionFactory, String queueName, MessageListener messageListener);
}
