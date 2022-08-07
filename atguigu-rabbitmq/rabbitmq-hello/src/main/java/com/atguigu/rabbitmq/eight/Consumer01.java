package com.atguigu.rabbitmq.eight;

import com.atguigu.rabbitmq.utils.RabbitmqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/*
* 死信队列
* */
public class Consumer01 {
    public static final String NORMAL_EXCHANGE = "normal_change";
    public static final String DEAD_EXCHANGE = "dead_change";
    public static final  String NORMAL_QUEUE = "normal_queue";
    public static final  String DEAD_QUEUE = "dead_queue";
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitmqUtils.getChannel();
        channel.exchangeDeclare(NORMAL_EXCHANGE, "topic");
//        死信信道
        channel.exchangeDeclare(DEAD_EXCHANGE, "topic");
        // 将死信数据传递给死信交换机
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        // 设置死信routing-key
        arguments.put("x-dead-letter-routing-key", "lisi");
        // 过期时间 10s
        // arguments.put("x-message-ttl", 10000);
        // 队列长度限制, 修改参数后,需先删除管理后台中的相应队列,才可以重新启动
        // arguments.put("x-max-length", 6);
        channel.queueDeclare(NORMAL_QUEUE, true, false, false, arguments);
        // 死信队列
        channel.queueDeclare(DEAD_QUEUE, true, false, false, null);

        channel.queueBind(NORMAL_QUEUE, NORMAL_EXCHANGE, "zhangsan");
        channel.queueBind(DEAD_QUEUE, DEAD_EXCHANGE, "lisi");
        System.out.println("等待接收消息:..");
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            String msg = new String(message.getBody(), "UTF-8");
            if(msg.equals("info5")) {
                System.out.println("拒绝消息为: " + msg);
                /*
                * 拒绝消息, 不返回消息队列
                * */
                channel.basicReject(message.getEnvelope().getDeliveryTag(), false);
            } else {
                System.out.println("C1接收消息为: " + new String(message.getBody(), "UTF-8"));
                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            }
        };
        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println("取消消息为: " + consumerTag.getBytes(StandardCharsets.UTF_8));
        };
        // 开启手动应答,不然拒绝消息时,消息不会插入到死信队列
        channel.basicConsume(NORMAL_QUEUE, false, deliverCallback, cancelCallback);
    }
}
