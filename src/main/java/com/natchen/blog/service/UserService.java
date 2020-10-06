package com.natchen.blog.service;

import com.natchen.blog.entity.User;
import com.natchen.blog.mapper.UserMapper;

import javax.inject.Inject;

public class UserService {
    private UserMapper userMapper;

    @Inject
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User getUserById(Integer id) {
        return userMapper.findUserById(id);
    }
}
