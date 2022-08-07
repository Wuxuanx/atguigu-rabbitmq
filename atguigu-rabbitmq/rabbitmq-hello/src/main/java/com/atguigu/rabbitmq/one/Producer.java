package com.atguigu.rabbitmq.one;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
//    队列名称
    public static final String QUEUE_NAME = "hello";

//    发送消息
    public static void main(String[] args) {
        // 连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 工厂IP
        connectionFactory.setHost("192.168.187.131");
        // 用户名
        connectionFactory.setUsername("admin");
        // 密码
        connectionFactory.setPassword("admin");
        // 创建连接
        try {
            Connection connection = connectionFactory.newConnection();
            // 获取信道
            Channel channel = connection.createChannel();
            // 生成一个队列
            /*
            * 1. 队列名
            * 2. 队列里消息是否持久化[磁盘中],默认存储在内存中
            * 3. 该队列是否只供一个消费者进行消费, 是否进行消息共享, true 可以多个消费者消费, false 只能一个消费者消费
            * 4. 是否自动删除, 最后一个消费者离开连接后,该队列是否自动删除, true 自动删除, false 不删除
            * 5. 其他参数
            * */
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            // 消息发送
            String message = "hello, WTD2!";
            /*
            * 1. 发送到哪个交换机
            * 2. 路由的key值
            * 3. 其他参数消息
            * 4. 发送消息的消息体[需要转为二进制]
            * */
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("消息发送完毕");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }
}
