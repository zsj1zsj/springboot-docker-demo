package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // 这是一个组合注解，包含了 @Configuration, @EnableAutoConfiguration, @ComponentScan
public class SpringbootCustomEditorApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootCustomEditorApplication.class, args);
    }
}