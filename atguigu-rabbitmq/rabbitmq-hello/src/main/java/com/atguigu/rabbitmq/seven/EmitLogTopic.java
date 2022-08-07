package com.atguigu.rabbitmq.seven;

import com.atguigu.rabbitmq.utils.RabbitmqUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class EmitLogTopic {
    public static final String EXCHANGE_NAME = "TOPIC_LOGS";
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitmqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        Map<String, String> bindingMap = new HashMap<>();
        bindingMap.put("quick.orange.rabbit", "Q1Q2");
        bindingMap.put("lazy.orange.elephant", "Q1Q2");
        bindingMap.put("quick.orange.fox", "Q1");
        bindingMap.put("lazy.brown.fox", "Q2");
        bindingMap.put("quick.brown.fox", "None");
        bindingMap.put("quick.orange.male.rabbit", "None");
        bindingMap.put("lazy.orange.male.rabbit", "Q2");
        bindingMap.put("quick.orange.rabbit", "Q1Q2");
        bindingMap.put("lazy.orange.elephant", "Q2Q1");
        bindingMap.put("quick.orange.fox", "Q2");
        for(Map.Entry<String, String> bindingKeyEntry : bindingMap.entrySet()){
            String routingKey = bindingKeyEntry.getKey();
            String message = bindingKeyEntry.getValue();
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("发送消息为: " + message);
        }
    }
}
