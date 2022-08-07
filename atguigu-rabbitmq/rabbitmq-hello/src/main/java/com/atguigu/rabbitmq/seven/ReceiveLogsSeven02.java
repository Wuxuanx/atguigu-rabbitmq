package com.atguigu.rabbitmq.seven;

import com.atguigu.rabbitmq.utils.RabbitmqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ReceiveLogsSeven02 {
    public static final String EXCHANGE_NAME = "TOPIC_LOGS";
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitmqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        channel.queueDeclare("q2", false, false, false, null);
        channel.queueBind("q2", EXCHANGE_NAME, "*.*.rabbit");
        channel.queueBind("q2", EXCHANGE_NAME, "lazy.#");
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("接收消息为: " + new String(message.getBody(), "UTF-8"));
            System.out.println("接收队列: q1 " + "绑定键: " + message.getEnvelope().getRoutingKey());
        };
        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println("取消消息为: " + consumerTag.toString());
        };
        channel.basicConsume("q1", true,  deliverCallback, cancelCallback);
    }
}
