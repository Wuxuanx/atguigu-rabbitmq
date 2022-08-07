package com.atguigu.rabbitmq.five;

import com.atguigu.rabbitmq.utils.RabbitmqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ReceiveLogs02 {
    public static final String EXCHANGE_NAME= "logs";
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitmqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");
        System.out.println("等待接收消息...");
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("02接收到的消息为: " + new String(message.getBody(), "UTF-8"));
        };
        channel.basicConsume(queueName, true, deliverCallback, (consumer)->{});
    }
}
