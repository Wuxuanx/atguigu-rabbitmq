package com.atguigu.rabbitmq.two;

/*
* 工作线程
* */

import com.atguigu.rabbitmq.utils.RabbitmqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Worker01 {
    // 队列名称
    public static final String QUEUE_NAME = "hello";

    // 接受消息
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitmqUtils.getChannel();
        // 消息接收
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("接收到的消息: " + new String(message.getBody()));
        };
        // 取消回调
        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println(consumerTag + ": 消费者取消接口回调逻辑");
        };
        System.out.println("C1等待接收消息....");
        // 是否手动应答
        boolean autoAck = false;
        // 消息接收
        channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, cancelCallback);
    }
}
