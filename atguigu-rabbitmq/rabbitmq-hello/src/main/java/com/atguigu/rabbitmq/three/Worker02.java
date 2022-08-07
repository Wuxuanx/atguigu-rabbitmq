package com.atguigu.rabbitmq.three;

import com.atguigu.rabbitmq.utils.RabbitmqUtils;
import com.atguigu.rabbitmq.utils.SleepUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/*
* 消息在手动应答时不丢失,放回消息队列
* */
public class Worker02 {
    public static final String TASK_QUEUE_NAME = "ack_queue";

//    消息处理
    public static void main (String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitmqUtils.getChannel();
        System.out.println("C1接收消息较快");
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            SleepUtils.sleep(5);
            System.out.println("接收消息: " + new String(message.getBody(), "UTF-8"));
            // 消息手动应答
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        };
        // 消息取消消费回调
        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println("取消消息处理!");
        };
        // 不公平分发,参数值为1
        // 预取值: 大于1时
        channel.basicQos(2);
        boolean autoAck = false;
        channel.basicConsume(TASK_QUEUE_NAME, autoAck, deliverCallback, cancelCallback);
    }

}
