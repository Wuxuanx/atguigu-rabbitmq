package com.atguigu.rabbitmq.five;

import com.atguigu.rabbitmq.utils.RabbitmqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ReceiveLogs01 {
    // 交换机名字
    public static final String EXCHANGE_NAME = "logs";
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitmqUtils.getChannel();
        // 声明一个交换机,采用发布定阅模式
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        /*
        * 声明一个临时队列,当消费者断开时,队列自动删除
        * */
        String queueName = channel.queueDeclare().getQueue();
        /*
        * 绑定交换机和队列
        * */
        channel.queueBind(queueName, EXCHANGE_NAME, "");
        System.out.println("等待接收消息....");
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("01接收到的消息为: " + new String(message.getBody(), "UTF-8"));
        };
        channel.basicConsume(queueName, true, deliverCallback, (CancelCallback) null);
    }
}
