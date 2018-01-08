package indi.zqc.rabbitmq.base.service.impl;

import indi.zqc.rabbitmq.base.constants.ExchangeType;
import indi.zqc.rabbitmq.base.model.MessageConsumer;
import indi.zqc.rabbitmq.base.model.MessageSender;
import indi.zqc.rabbitmq.base.service.MQAccessBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SimpleMQAccessBuilder implements MQAccessBuilder {

    private Logger logger = LoggerFactory.getLogger(SimpleMQAccessBuilder.class);

    @Resource
    private ConnectionFactory connectionFactory;

    @Override
    public MessageSender buildMessageSender(String queueName) {
        return buildMessageSender(connectionFactory, queueName);
    }

    @Override
    public MessageSender buildMessageSender(ConnectionFactory connectionFactory, String queueName) {
        return buildMessageSender(queueName, queueName, queueName, ExchangeType.DIRECT);
    }

    @Override
    public MessageSender buildMessageSender(String exchangeName, String routingKey, String queueName, ExchangeType exchangeType) {
        return buildMessageSender(connectionFactory, exchangeName, routingKey, queueName, exchangeType);
    }

    @Override
    public MessageSender buildMessageSender(ConnectionFactory connectionFactory, String exchangeName, String routingKey, String queueName, ExchangeType exchangeType) {
        List<String> queueNames = new ArrayList<>();
        queueNames.add(queueName);
        MessageSender messageSender = new MessageSender(connectionFactory, exchangeName, routingKey, queueNames, exchangeType);
        messageSender.build();
        return messageSender;
    }

    @Override
    public MessageConsumer buildMessageConsumer(String queueName, MessageListener messageListener) {
        return buildMessageConsumer(connectionFactory, queueName, messageListener);
    }

    @Override
    public MessageConsumer buildMessageConsumer(ConnectionFactory connectionFactory, String queueName, MessageListener messageListener) {
        List<String> queueNames = new ArrayList<>();
        queueNames.add(queueName);
        MessageConsumer messageConsumer = new MessageConsumer(connectionFactory, queueNames, messageListener);
        return messageConsumer;
    }
}
