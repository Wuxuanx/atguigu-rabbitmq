package com.atguigu.rabbitmq.three;

/*
* 消息在手动应答时不丢失,放回消息队列
* */

import com.atguigu.rabbitmq.utils.RabbitmqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;
import java.util.stream.StreamSupport;

public class Task2 {
    public static final String TASK_QUEUE_NAME = "ack_queue";

    // 消息处理
    public static void main(String[] args) throws IOException, TimeoutException {

        Channel channel = RabbitmqUtils.getChannel();
        /*
         * 1. 队列名
         * 2. 队列里消息是否持久化[磁盘中],默认存储在内存中
         * 3. 该队列是否只供一个消费者进行消费, 是否进行消息共享, true 可以多个消费者消费, false 只能一个消费者消费
         * 4. 是否自动删除, 最后一个消费者离开连接后,该队列是否自动删除, true 自动删除, false 不删除
         * 5. 其他参数
         * */
        boolean durable = true;
        channel.queueDeclare(TASK_QUEUE_NAME, durable, false, false, null);
        // 开启发布确认
        channel.confirmSelect();
        // 从控制台输入消息
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()) {
            String message = scanner.next();
            // 设置生产者发送消息为持久化消息,保存到磁盘上: MessageProperties.PERSISTENT_TEXT_PLAIN
            channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
            System.out.println("发送消息: " + message);
        }

    }

}
