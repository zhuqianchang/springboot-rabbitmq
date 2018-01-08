package indi.zqc.rabbitmq.base.configuration;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Title : RabbitMQConfiguration.java
 * Package : indi.zqc.rabbitmq.base.configuration
 * Description : 消息服务器配置
 * Create on : 2018/1/8 14:50
 * 
 * @author Zhu.Qianchang
 * @version v1.0.0
 */
@Configuration
public class RabbitMQConfiguration {

    @Value("${spring.rabbitmq.addresses:}")
    private String addresses;

    @Value("${spring.rabbitmq.username:}")
    private String username;

    @Value("${spring.rabbitmq.password:}")
    private String password;

    @Value("${spring.rabbitmq.virtual-host:}")
    private String vhost;

    /**
     * 消息服务器配置
     * @return
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(addresses);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(vhost);
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }
}
