package indi.zqc.rabbitmq.producer;

import indi.zqc.rabbitmq.base.constants.ExchangeType;
import indi.zqc.rabbitmq.base.model.MessageSender;
import indi.zqc.rabbitmq.base.service.MQAccessBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = "indi.zqc.rabbitmq")
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplication(Application.class).run(args);
        MQAccessBuilder mqAccessBuilder = context.getBean(MQAccessBuilder.class);
        MessageSender messageSender = mqAccessBuilder.buildMessageSender("queue");
        String message = "rabbitmq message";
        messageSender.send(message);
        System.out.println("生产端发送消息 : " + message);

        MessageSender messageSender2 = mqAccessBuilder.buildMessageSender("topic", "*.topic.*", "topic", ExchangeType.TOPIC);
        String topicMessage = "rabbitmq topic message";
        messageSender2.send("123.topic.234", topicMessage);
        System.out.println("生产端发送消息 : " + topicMessage);
    }

}
