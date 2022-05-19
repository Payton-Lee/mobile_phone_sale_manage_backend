package com.phoneshop.shop.controller;

import cn.hutool.core.lang.Dict;
import com.phoneshop.shop.entity.result.ResultData;
import com.phoneshop.shop.entity.User;
import com.phoneshop.shop.entity.enums.ReturnCode;
import com.phoneshop.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/v1")
public class LoginRegController {
    private UserService userService;
    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }
    @PostMapping("/login")
    public Object login(@RequestParam("username") String userName, @RequestParam("password") String password) {
        Dict result = Dict.create();
        ResultData<Object> resultData;
        if(!userService.isExist(userName)){
            resultData = ResultData.fail(ReturnCode.USERNAME_ERROR.code, ReturnCode.USERNAME_ERROR.message);
        } else {
            // 获取数据库中的同名用户
            User userInDB = userService.getByUserName(userName);
            // 判断当前用户是否可以用
            if(userInDB.getState() != 1) {
                resultData = ResultData.fail(ReturnCode.USER_DENIED.code, ReturnCode.USER_DENIED.message);
            } else {
                // 对密码进行hash判断
                if (userService.verifyPassword(password, userInDB.getPassword())) {
                    String token = userService.getToken(userInDB);
                    result.set("username", userInDB.getUsername()).set("msg", "登录成功").set("token", token);
                    resultData = ResultData.success(ReturnCode.RC200.code, ReturnCode.RC200.message, result);
                } else {
                    resultData = ResultData.fail(ReturnCode.PASSWORD_ERROR.code, ReturnCode.PASSWORD_ERROR.message);
                }
            }
        }
        return resultData;
    }
    @PostMapping("/register")
    public Object register(@RequestBody User user) {
        Dict result = Dict.create();
        ResultData<Object> resultData;
        if(userService.isExist(user.getUsername())) {
            resultData = ResultData.fail(ReturnCode.USERNAME_EXIST.code, ReturnCode.USERNAME_EXIST.message);
        } else {
            user.setPassword(userService.getEncryptedPassword(user.getPassword()));
            if(user.getState() == null) {
                user.setState(0);
            }
            user.setCreateTime(LocalDateTime.now());
            if(userService.register(user)){
                result.set("username", user.getUsername()).set("msg", "注册成功");
                resultData = ResultData.success(ReturnCode.RC201.code, ReturnCode.RC201.message, result);
            } else {
                resultData = ResultData.fail(ReturnCode.RC999.code, ReturnCode.RC999.message);
            }
        }
        return resultData;
    }
}
