package com.natchen.blog.controller;

import com.natchen.blog.entity.Result;
import com.natchen.blog.entity.User;
import com.natchen.blog.service.UserService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Map;

@Controller
public class AuthController {
    private UserService userService;
    private AuthenticationManager authenticationManager;

    @Inject
    public AuthController(UserService userService,
                          AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/auth")
    @ResponseBody
    public Result auth() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedInUser = userService.getUserByUsername(username);

        if (loggedInUser == null) {
            return Result.success("用户没有登录", false, null);
        } else {
            return Result.success("success", true, loggedInUser);
        }
    }

    @GetMapping("/auth/logout")
    @ResponseBody
    public Object logout() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedUser = userService.getUserByUsername(username);

        if (loggedUser == null) {
            return Result.failure("用户没有登录", false);
        } else {
            SecurityContextHolder.clearContext();
            return Result.success("success", false, null);
        }
    }

    @PostMapping("/auth/register")
    @ResponseBody
    public Result register(@RequestBody Map<String, String> usernameAndPassword) {
        String username = usernameAndPassword.get("username");
        String password = usernameAndPassword.get("password");

        if (username == null || password == null) {
            return Result.failure("username/password == null", false);
        }

        if (username.length() < 1 || username.length() > 15) {
            return Result.failure("invalid username", false);
        }

        if (password.length() < 1 || password.length() > 15) {
            return Result.failure("invalid password", false);
        }

        try {
            userService.save(username, password);
            return Result.success("success", false, null);
        } catch (DuplicateKeyException e) {
            return Result.failure("user already exists", false);
        }
    }

    @PostMapping("/auth/login")
    @ResponseBody
    public Result login(@RequestBody Map<String, Object> usernameAndPassword) {
        String username = usernameAndPassword.get("username").toString();
        String password = usernameAndPassword.get("password").toString();

        UserDetails userDetails;

        try {
            userDetails = userService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            return Result.failure( "用户不存在", false);
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password);

        try {
            authenticationManager.authenticate(token);
            //save cookie
            SecurityContextHolder.getContext().setAuthentication(token);
            return Result.success("登录成功", true, userService.getUserByUsername(username));
        } catch (BadCredentialsException e) {
            return Result.failure("密码不正确",false);
        }
    }
}
