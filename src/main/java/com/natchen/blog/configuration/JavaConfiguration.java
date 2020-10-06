package com.natchen.blog.configuration;

import com.natchen.blog.mapper.UserMapper;
import com.natchen.blog.service.OrderService;
import com.natchen.blog.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaConfiguration {
    @Bean
    public UserService userService(UserMapper userMapper) {
        return new UserService(userMapper);
    }

    @Bean
    public OrderService orderService(UserService userService) {
        return new OrderService(userService);
    }
}
