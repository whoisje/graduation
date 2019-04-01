package com.oe.account;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wangwj
 * @data 2019/3/29
 */
@SpringBootApplication
@MapperScan(value = "com.oe.account.mapper")
public class OeApplication {
    public static void main(String[] args) {
        SpringApplication.run(OeApplication.class);
    }
}
