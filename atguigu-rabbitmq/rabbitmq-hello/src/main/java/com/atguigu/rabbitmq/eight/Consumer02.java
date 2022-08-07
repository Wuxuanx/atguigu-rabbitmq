package com.atguigu.rabbitmq.eight;

import com.atguigu.rabbitmq.utils.RabbitmqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Consumer02 {
    public static final String DEAD_CHANGE = "dead_change";
    public static final String DEAD_QUEUE = "dead_queue";
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitmqUtils.getChannel();
        channel.exchangeDeclare(DEAD_CHANGE, "topic");
        channel.queueDeclare(DEAD_QUEUE, true, false, false, null);
        channel.queueBind(DEAD_QUEUE, DEAD_CHANGE, "lisi");
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("C2接收消息为: " + new String(message.getBody(), "UTF-8"));
        };
        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println("取消消息为: " + consumerTag.getBytes(StandardCharsets.UTF_8));
        };
        channel.basicConsume(DEAD_QUEUE, true, deliverCallback, cancelCallback);
    }
}
