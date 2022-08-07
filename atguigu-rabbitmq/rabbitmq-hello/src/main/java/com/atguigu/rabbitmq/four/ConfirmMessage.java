package com.atguigu.rabbitmq.four;

import com.atguigu.rabbitmq.utils.RabbitmqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeoutException;

public class ConfirmMessage {

    // 批量发消息个数
    public static final int MESSAGE_COUNT = 1000;

    public static void main(String[] args) throws IOException, InterruptedException, TimeoutException {
        /*
        * 1. 单个确认
        * 2. 批量确认
        * 3. 异步批量确认
        *
        *
        * */
        // ConfirmMessage.publishMessageIndividually(); // 542ms
        // ConfirmMessage.publishBatch(); // 99ms
        ConfirmMessage.publishMessageAsync(); // 41ms
    }
    // 单个确认
    public static void publishMessageIndividually() throws IOException, TimeoutException, InterruptedException {
        Channel channel = RabbitmqUtils.getChannel();
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, true, false, false, null);
        // 开启发布确认
        channel.confirmSelect();
        // 开始时间
        long begin = System.currentTimeMillis();
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = i + "";
            channel.basicPublish("", queueName,null, message.getBytes());
            boolean flag = channel.waitForConfirms();
            if(flag) {
                System.out.println("发送成功");
            }
        }
        // 结束时间
        long end = System.currentTimeMillis();
        System.out.println("发布" + MESSAGE_COUNT + "条单独确认消息,耗时: " + (end - begin) + " ms");
    }
    // 批量发布确认
    public static void publishBatch() throws IOException, TimeoutException, InterruptedException {
        Channel channel = RabbitmqUtils.getChannel();
        channel.confirmSelect();
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, true, false, false, null);
        long start = System.currentTimeMillis();
        // 批量确认消息长度
        int batchSize = 100;
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = i + "";
            channel.basicPublish("", queueName, null, message.getBytes());
            // 判断达到100条时批量确认一次
            if(i % batchSize == 0) {
                channel.waitForConfirms();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("发布" + MESSAGE_COUNT + "条数据,耗时: " + (end - start) + "ms");
    }
    // 异步确认
    public static void publishMessageAsync() throws IOException, TimeoutException {
        Channel channel = RabbitmqUtils.getChannel();
        channel.confirmSelect();
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, false, false, false, null);
        /*
        * 线程安全有序的一个哈希表,适用于高并发
        * 1. 轻松的将序号和消息进行关联
        * 2. 轻松批量删除条目, 只要给序号
        * 3. 支持高并发(多线程)
        * */
        ConcurrentSkipListMap<Long, String> outstandingConfirms = new ConcurrentSkipListMap<>();
        // 创建临时队列
//        String queueName = channel.queueDeclare().getQueue();
        long start = System.currentTimeMillis();
        // 消息监听器
        // 消息确认成功回调函数
        ConfirmCallback ackConfirmCallback = (deliveryTag, multiple) -> {
            if(multiple) {
                // 2. 删除确认的消息
                ConcurrentNavigableMap<Long, String> confirmed = outstandingConfirms.headMap(deliveryTag);
                confirmed.clear();
            } else {
                outstandingConfirms.remove(deliveryTag);
            }
            System.out.println("确认的消息编号: " + deliveryTag);

        };
        // 消息确认失败回调函数
        /*
        * 1. 消息标记
        * 2. 是否批量确认
        * */
        ConfirmCallback nackConfirmCallback = (deliveryTag, multiple) -> {
            // 3. 打印未确认消息有哪些
            String message = outstandingConfirms.get(deliveryTag);
            System.out.println("未确认消息: " + deliveryTag + " :  " + message);
        };
        /*
        * 1. 表示监听哪些消息成功了
        * 2. 表示监听哪些消息失败了
        * */
        channel.addConfirmListener(ackConfirmCallback, nackConfirmCallback);
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = i + "";
            channel.basicPublish("", queueName, null, message.getBytes());
            // 1. 记录所有要发布的消息
            outstandingConfirms.put(channel.getNextPublishSeqNo(), message);
        }
        long end = System.currentTimeMillis();
        System.out.println("发送" + MESSAGE_COUNT + "条信息,异步确认耗时: " + (end - start) + " ms");

    }
}
