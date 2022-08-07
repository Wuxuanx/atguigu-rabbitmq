package com.atguigu.rabbitmq.springbootrabbitmq.controller;

import groovy.util.logging.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/ttl")
public class SendMessageController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // 开始发消息
    @GetMapping("/sendMsg/{message}")
    public void sendMsg(@PathVariable String message) {
        System.out.printf("当前时间为: {}, 发送一条消息给连兵哥哥TTL队列:{}",
                new Date().toString(), message);
        rabbitTemplate.convertAndSend("X", "XA",
                "消息来自ttl1为10s的队列" + message);
        rabbitTemplate.convertAndSend("X", "XB",
                "消息来自ttl1为40s的队列" + message);
    }
}
