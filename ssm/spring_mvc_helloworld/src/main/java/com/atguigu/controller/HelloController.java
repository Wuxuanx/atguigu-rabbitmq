package com.atguigu.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class HelloController {

    @RequestMapping("/")
    public String protal() {
//        将逻辑视图返回
        System.out.println("111111111111");
        log.debug("11111111111111");
        return "index";
    }

    @RequestMapping("/hello")
    public String hello() {
//        将逻辑视图返回
        return "hello";
    }

}
