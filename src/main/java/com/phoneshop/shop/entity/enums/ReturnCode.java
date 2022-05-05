package com.phoneshop.shop.entity.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ReturnCode {
    RC200(200, "请求成功"),
    RC201(201, "操作成功"),
    RC403(403, "无访问权限，请联系管理员授予权限"),
    RC500(500, "系统异常，请稍后重试"),
    RC999(999, "请求失败"),
    INVALID_TOKEN(2001, "访问令牌不合法"),
    ACCESS_DENIED(2003, "没有权限访问资源"),
    USERNAME_NOT_EXIST(1000, "用户名不存在"),
    USERNAME_EXIST(1001, "用户名已存在"),
    USERNAME_ERROR(1002,"用户名错误"),
    PASSWORD_ERROR(1003, "密码错误"),
    USER_DENIED(1004, "用户被禁用");


    public final int code;
    public final String message;

}
