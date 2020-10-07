package com.natchen.blog.entity;

public class Result {
    String status;
    String msg;
    boolean isLogin;
    Object data;

    public static Result success(String message, Boolean isLogin, User user) {
        return new Result("ok", message, isLogin, user);
    }

    public static Result failure(String message, Boolean isLogin) {
        return new Result("fail", message, isLogin);
    }

    private Result(String status, String msg, boolean isLogin) {
        this(status, msg, isLogin, null);
    }

    private Result(String status, String msg, boolean isLogin, User loggedUser) {
        this.status = status;
        this.msg = msg;
        this.isLogin = isLogin;
        this.data = loggedUser;
    }

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public Object getData() {
        return data;
    }
}
