package com.tjh.springboot02amqp;

import com.tjh.springboot02amqp.bean.Book;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class Springboot02AmqpApplicationTests {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    AmqpAdmin amqpAdmin;

    /**
     * 1、单播（点对点）
     */
    @Test
    void convertAndSend() {
        //Message需要自己构造一个：定义消息体内容和消息头
        //rabbitTemplate.send(exchange,routeKey,message);

        //object默认当成消息体，只需要传入要发送的对象，自动序列化发送给rabbitmq
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "这是第一个消息");
        map.put("data", Arrays.asList("helloworld", 123, true));
        rabbitTemplate.convertAndSend("exchange.direct", "atguigu.news", map);
    }

    /**
     * 测试发送一个实体
     */
    @Test
    void entityConvertAndSend() {
        rabbitTemplate.convertAndSend("exchange.direct", "atguigu.news", new Book("西游记", "吴承恩"));
    }

    /**
     * 接受数据
     */
    @Test
    public void receive() {
        Object o = rabbitTemplate.receiveAndConvert("atguigu.news");
        System.out.println(o.getClass());
        System.out.println(o);
    }

    /**
     * 广播
     */
    @Test
    public void sendMsg() {
        rabbitTemplate.convertAndSend("exchange.fanout", "", new Book("三国", "罗贯中"));
    }

    /**
     * 创建Exchage交换器
     */
    @Test
    public void createExchange() {
        amqpAdmin.declareExchange(new DirectExchange("amqpadmin.exchang"));
        System.out.println("Exchange创建完成");
    }

    /**
     * 创建Queue队列
     */
    @Test
    public void createQueue() {
        amqpAdmin.declareQueue(new Queue("amqpadmin.queue", true));
        System.out.println("Queue创建完成");
    }

    /**
     * 创建Binding绑定规则
     */
    @Test
    public void createBinding() {
        amqpAdmin.declareBinding(new Binding("amqpadmin.queue", Binding.DestinationType.QUEUE, "amqpadmin.exchang", "amqp.haha", null));
        System.out.println("Binding创建完成");
    }

}
