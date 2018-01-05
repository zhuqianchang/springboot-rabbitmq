package indi.zqc.rabbitmq.producer.test;

import indi.zqc.rabbitmq.producer.service.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Title : MessageServiceTest.java
 * Package : indi.zqc.rabbitmq.producer.test
 * Description : 消息接口测试
 * Create on : 2018/1/5 14:29
 *
 * @author Zhu.Qianchang
 * @version v1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class})
public class MessageServiceTest {

    @Resource
    private MessageService messageService;

    @Test
    public void DirectTest() {
        messageService.sendDirectMessage("routingKey", "direct message", "queue");
    }

    @Test
    public void TopicTest() {
        messageService.sendTopicMessage("routingKey", "topic message", "queue", "queue2");
    }

    @Test
    public void FanoutTest() {
        messageService.sendFanoutMessage("fanout message", "queue");
    }
}
