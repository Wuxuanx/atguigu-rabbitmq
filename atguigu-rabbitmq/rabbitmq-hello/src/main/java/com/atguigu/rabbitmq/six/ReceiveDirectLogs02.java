package com.atguigu.rabbitmq.six;

import com.atguigu.rabbitmq.utils.RabbitmqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class ReceiveDirectLogs02 {
    public static final String EXCHANGE_NAME = "direct_logs";
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitmqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        // String queueName = channel.queueDeclare().getQueue();
        channel.queueDeclare("disk", true, false, false, null);
        channel.queueBind("disk", EXCHANGE_NAME, "error");
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("接收到的消息为: " + new String(message.getBody(), "UTF-8"));
        };
        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println("取消消息为: " + consumerTag.getBytes(StandardCharsets.UTF_8));
        };
        channel.basicConsume("disk", true, deliverCallback, cancelCallback);
    }
}
