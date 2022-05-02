package com.phoneshop.shop.controller;

import com.phoneshop.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    private UserService userService;
    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }
    @PostMapping("/login")
    public Object login(@RequestParam("username") String userName, @RequestParam("password") String password) {


        if(!userService.isExist(userName)){

        }
        return null;
    }

}
