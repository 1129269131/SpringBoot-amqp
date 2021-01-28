package com.tjh.springboot02amqp.service;

import com.tjh.springboot02amqp.bean.Book;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    /**
     * 接收消息体信息
     *
     * @param book
     */
    @RabbitListener(queues = "atguigu.news")
    public void receive(Book book) {
        System.out.println("收到消息：" + book);
    }

    /**
     * 接收头信息
     *
     * @param message
     */
    @RabbitListener(queues = "atguigu")
    public void receive02(Message message) {
        System.out.println(message.getBody());
        System.out.println(message.getMessageProperties());
    }
}
