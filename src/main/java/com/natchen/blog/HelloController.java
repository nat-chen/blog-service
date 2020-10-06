package com.natchen.blog;

import com.natchen.blog.entity.User;
import com.natchen.blog.service.UserService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

@RestController
public class HelloController {
    private UserService userService;

    @Inject
    public HelloController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public User index() {
        return userService.getUserById(1);
    }

}