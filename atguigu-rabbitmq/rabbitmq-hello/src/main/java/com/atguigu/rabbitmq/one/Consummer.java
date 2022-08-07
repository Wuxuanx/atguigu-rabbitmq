package com.atguigu.rabbitmq.one;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consummer {
    // 队列名称
    public static final String QUEUE_NAME = "hello";
//    接收消息
    public static void main(String[] args) {
        // 创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 设置工厂信息
        connectionFactory.setPassword("admin");
        connectionFactory.setUsername("admin");
        connectionFactory.setHost("192.168.187.131");
        // 创建连接
        try {
            Connection connection = connectionFactory.newConnection();
            // 创建信道
            Channel channel = connection.createChannel();
            // 声明接受消息
//            DeliverCallback deliverCallback = new DeliverCallback() {
//                @Override
//                public void handle(String s, Delivery delivery) throws IOException {
//                    System.out.println(s);
//                }
//            };
            DeliverCallback deliverCallback = (consumerTag, message) -> {
                System.out.println(new String(message.getBody()));
            };
            // 取消消息时回调
            CancelCallback cancelCallback = consumerTag -> {
                System.out.println("消息消费被中断!");
            };
            // 接收消息
            /*
            * 1. 消费队列名
            * 2. 消费成功后是否自动应答, true 自动应答, false 手动应答
            * 3. 消费者未消费成功时的回调
            * 4. 消费者取消消费的回调
            * */
            // 是否手动应答
            boolean autoAck = false;
            channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, cancelCallback);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
