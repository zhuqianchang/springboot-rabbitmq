package indi.zqc.rabbitmq.producer.service.impl;

import indi.zqc.rabbitmq.producer.constants.ExchangeType;
import indi.zqc.rabbitmq.producer.service.MessageService;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * Title : MessageServiceImpl.java
 * Package : indi.zqc.rabbitmq.producer.service.impl
 * Description : 消息服务实现
 * Create on : 2018/1/5 14:13
 *
 * @author Zhu.Qianchang
 * @version v1.0.0
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private RabbitAdmin rabbitAdmin;

    private Set<String> declaredExchangeAndQueues = new HashSet<>();

    private static final String DIRECT_EXCHANGE = "DIRECT_EXCHANGE";
    private static final String TOPIC_EXCHANGE = "TOPIC_EXCHANGE";
    private static final String FANOUT_EXCHANGE = "FANOUT_EXCHANGE";

    @Override
    public void sendDirectMessage(String routingKey, Object data, String queueName) {
        declareExchangeAndQueue(DIRECT_EXCHANGE, ExchangeType.DIRECT, routingKey, queueName);
        rabbitTemplate.convertAndSend(DIRECT_EXCHANGE, routingKey, data);
    }

    @Override
    public void sendFanoutMessage(Object data, String queueName) {
        declareExchangeAndQueue(FANOUT_EXCHANGE, ExchangeType.FANOUT, null, queueName);
        rabbitTemplate.convertAndSend(FANOUT_EXCHANGE, null, data);
    }

    @Override
    public void sendTopicMessage(String routingKey, Object data, String... queueNames) {
        declareExchangeAndQueue(TOPIC_EXCHANGE, ExchangeType.TOPIC, routingKey, queueNames);
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE, routingKey, data);
    }

    /**
     * 绑定交换器和队列的关系
     * @param exchangeName
     * @param ExchangeType
     * @param routingKey
     * @param queueNames
     */
    private void declareExchangeAndQueue(String exchangeName, ExchangeType ExchangeType, String routingKey,
                                         String... queueNames) {
        if (queueNames != null && queueNames.length > 0) {
            for (String queueName : queueNames) {
                if (!declaredExchangeAndQueues.contains(exchangeName + "|" + queueName)) {
                    Queue queue = new Queue(queueName);
                    queue.setAdminsThatShouldDeclare(rabbitAdmin);
                    rabbitAdmin.declareQueue(queue);

                    switch (ExchangeType) {
                        case TOPIC:
                            TopicExchange topicExchange = new TopicExchange(exchangeName);
                            rabbitAdmin.declareExchange(topicExchange);
                            rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(topicExchange).with(routingKey));
                            break;
                        case DIRECT:
                            DirectExchange directExchange = new DirectExchange(exchangeName);
                            rabbitAdmin.declareExchange(directExchange);
                            rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(directExchange).with(routingKey));
                            break;
                        case FANOUT:
                            FanoutExchange fanoutExchange = new FanoutExchange(exchangeName);
                            rabbitAdmin.declareExchange(fanoutExchange);
                            rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(fanoutExchange));
                            break;
                        default:
                            FanoutExchange exchange = new FanoutExchange(exchangeName);
                            rabbitAdmin.declareExchange(exchange);
                            rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange));
                            break;
                    }
                    declaredExchangeAndQueues.add(exchangeName + "|" + queueName);
                }
            }
        }
    }
}
