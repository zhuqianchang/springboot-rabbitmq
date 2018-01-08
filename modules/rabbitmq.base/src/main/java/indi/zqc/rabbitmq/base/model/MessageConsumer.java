package indi.zqc.rabbitmq.base.model;

import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

import java.util.ArrayList;
import java.util.List;

/**
 * Title : Customer.java
 * Package : indi.zqc.rabbitmq.base.model
 * Description : 消费者
 * Create on : 2018/1/5 9:46
 *
 * @author Zhu.Qianchang
 * @version v1.0.0
 */
public class MessageConsumer {

    private SimpleMessageListenerContainer container;

    private ConnectionFactory connectionFactory;

    private RabbitAdmin rabbitAdmin;

    private List<String> queueNames = new ArrayList<>();

    private MessageListener messageListener;

    public MessageConsumer(ConnectionFactory connectionFactory, List<String> queueNames, MessageListener messageListener) {
        this.connectionFactory = connectionFactory;
        this.queueNames = queueNames;
        this.messageListener = messageListener;
        this.rabbitAdmin = new RabbitAdmin(connectionFactory);
    }

    /**
     * 开启消息监听
     */
    public void start() {
        validate();
        container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        if (queueNames != null) {
            for (String queueName : queueNames) {
                Queue queue = new Queue(queueName);
                queue.setAdminsThatShouldDeclare(rabbitAdmin);
                rabbitAdmin.declareQueue(queue);
                container.addQueueNames(queueName);
            }
        }
        container.setMessageListener(messageListener);
        container.start();
    }

    /**
     * 停止消息监听
     */
    public void stop() {
        if (isRunning()) {
            container.stop();
        }
    }

    /**
     * 监听是否启动
     * @return
     */
    public boolean isRunning() {
        return container != null && container.isRunning();
    }

    /**
     * 校验
     */
    public void validate() {
        if (connectionFactory == null) {
            throw new RuntimeException("消息服务器配置不能为空！");
        }
        if (messageListener == null) {
            throw new RuntimeException("消息监听类不能为空");
        }
    }

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
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

    public MessageListener getMessageListener() {
        return messageListener;
    }

    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }
}
