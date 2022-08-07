package com.atguigu.rabbitmq.three;

import com.atguigu.rabbitmq.utils.RabbitmqUtils;
import com.atguigu.rabbitmq.utils.SleepUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/*
* 消息手动应答,取消消息放回消息队列
* */
public class Worker02_1 {
    public static final String ACK_QUEUE_NAME = "ack_queue";
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitmqUtils.getChannel();
        System.out.println("C2处理消息较慢!");
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            SleepUtils.sleep(30);
            System.out.println("接收消息为: " + new String(message.getBody(), "UTF-8"));
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        };
        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println("取消消息应答!!!");
        };
        // 不公平分发;参数值为1
        // 预取值: 值为大于1时
        channel.basicQos(5);
        boolean autoAck = false;
        channel.basicConsume(ACK_QUEUE_NAME, autoAck, deliverCallback, cancelCallback);
    }
}
