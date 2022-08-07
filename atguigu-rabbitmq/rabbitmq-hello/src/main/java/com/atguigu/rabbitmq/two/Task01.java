package com.atguigu.rabbitmq.two;

import com.atguigu.rabbitmq.utils.RabbitmqUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/*
* 生产者,发送大量消息
* */
public class Task01 {
    // 队列名称
    public static final String QUEUE_NAME = "hello";
//    发送大量消息
    public static void main (String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitmqUtils.getChannel();
        // 声明队列
        /*
        * 1. 队列名称
        * 2. 是否持久化
        * 3. 是否消息共享
        * 4. 是否自动删除
        * 5. 其它参数
        * */
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        // 从控制台接收发送消息
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()) {
            String message = scanner.next();
            /*
            * 1. 交换金名称
            * 2. 消息队列名称
            * 3. 其它参数
            * 4. 消息内容
            * */
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("发送消息完成: " + message);
        }
    }
}
