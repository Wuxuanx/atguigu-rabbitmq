package com.atguigu.rabbitmq.utils;

/*
* 睡眠工具类
* */
public class SleepUtils {
    public static void sleep (int second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
