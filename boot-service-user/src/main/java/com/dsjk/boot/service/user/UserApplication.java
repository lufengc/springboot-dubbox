package com.dsjk.boot.service.user;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

@SpringBootApplication
@ComponentScan(basePackages = "com.dsjk.boot.common,com.dsjk.boot.service.user")
@MapperScan(basePackages = "com.dsjk.boot.service.user.mapper", annotationClass = Repository.class)
public class UserApplication {

    private static final Logger logger = LoggerFactory.getLogger(UserApplication.class);

    private static final Object lock = new Object();

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);

        try {
            synchronized (lock) {
                lock.wait();
            }
        } catch (Exception e) {
            logger.error("服务关闭，主线程异常：{}" + e);
        }
    }
}
