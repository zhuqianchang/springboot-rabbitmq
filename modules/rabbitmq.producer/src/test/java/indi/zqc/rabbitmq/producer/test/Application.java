package indi.zqc.rabbitmq.producer.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("indi.zqc.rabbitmq.producer")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}