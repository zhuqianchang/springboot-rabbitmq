package indi.zqc.rabbitmq.customer;

import indi.zqc.rabbitmq.base.service.MQAccessBuilder;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
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
        mqAccessBuilder.buildMessageConsumer("queue", new MessageListener() {
            @Override
            public void onMessage(Message message) {
                byte[] body = message.getBody();
                System.out.println("消费端接收到消息 : " + new String(body));
            }
        }).start();

        mqAccessBuilder.buildMessageConsumer("topic", new MessageListener() {
            @Override
            public void onMessage(Message message) {
                byte[] body = message.getBody();
                System.out.println("消费端接收到消息 : " + new String(body));
            }
        }).start();
    }
}
