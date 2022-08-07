package com.atguigu.rabbitmq.eight;

import com.atguigu.rabbitmq.utils.RabbitmqUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    public static final String NORMAL_EXCHANGE = "normal_change";
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitmqUtils.getChannel();
        // 设置消息TTL时间
        // AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().expiration("10000").build();
        for(int i = 0; i < 11; i++) {
            String message = "info" + i;
            channel.basicPublish(NORMAL_EXCHANGE, "zhangsan", null, message.getBytes());
        }

    }
}
