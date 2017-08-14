package com.dsjk.boot.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
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
