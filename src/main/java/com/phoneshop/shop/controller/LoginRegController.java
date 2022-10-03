package com.phoneshop.shop.controller;

import cn.hutool.core.lang.Dict;
import com.phoneshop.shop.entity.result.ResultData;
import com.phoneshop.shop.entity.User;
import com.phoneshop.shop.entity.enums.ReturnCode;
import com.phoneshop.shop.service.PermissionService;
import com.phoneshop.shop.service.RoleService;
import com.phoneshop.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class LoginRegController {
    private UserService userService;
    private PermissionService permissionService;
    private RoleService roleService;
    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }
    @Autowired
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }
    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }
    @PostMapping("/login")
    public Object login(@RequestBody User user) {
        Dict result = Dict.create();
        if(!userService.isExist(user.getUsername())){
            return ResultData.fail(ReturnCode.USERNAME_ERROR.code, ReturnCode.USERNAME_ERROR.message);
        } else {
            // 获取数据库中的同名用户
            User userInDB = userService.getByUserName(user.getUsername());
            var userRoleVos = roleService.findRoleListByUserId(user.getId());
            if(userRoleVos.stream().flatMap(userRoleVo -> permissionService.findPermissionByRoleId(userRoleVo.getRoleId()).stream())
                    .anyMatch(rolePermissionVo -> rolePermissionVo.getType() == 3)) {
                return ResultData.fail(ReturnCode.USER_DENIED.code, ReturnCode.USER_DENIED.message);
            }
            // 判断当前用户是否可以用
            if(userInDB.getState() != 1) {
                return ResultData.fail(ReturnCode.USER_DENIED.code, ReturnCode.USER_DENIED.message);
            } else {
                // 对密码进行hash判断
                if (userService.verifyPassword(user.getPassword(), userInDB.getPassword())) {
                    String token = userService.getToken(userInDB);
                    result.set("username", userInDB.getUsername()).set("msg", "登录成功").set("token", token);
                    return ResultData.success(ReturnCode.RC200.code, ReturnCode.RC200.message, result);
                } else {
                    return ResultData.fail(ReturnCode.PASSWORD_ERROR.code, ReturnCode.PASSWORD_ERROR.message);
                }
            }
        }
    }
    @PostMapping("/register")
    public Object register(@RequestBody User user) {
        Dict result = Dict.create();
        ResultData<Object> resultData;
        if(userService.isExist(user.getUsername())) {
            // 判断用户是否存在，存在则不允许注册
            resultData = ResultData.fail(ReturnCode.USERNAME_EXIST.code, ReturnCode.USERNAME_EXIST.message);
        } else {
            // 把密码加密
            user.setPassword(userService.getEncryptedPassword(user.getPassword()));
            // 设置状态，默认值为0
            if(user.getState() == null) {
                user.setState(0);
            }
            // 设置当前用户的创建时间
            user.setCreateTime(LocalDateTime.now());
            if(userService.register(user)){
                result.set("username", user.getUsername()).set("msg", "注册成功, 等待管理员审核！");
                resultData = ResultData.success(ReturnCode.RC201.code, ReturnCode.RC201.message, result);
            } else {
                result.set("msg", "注册失败，请重试！");
                resultData = ResultData.fail(ReturnCode.RC999.code, ReturnCode.RC999.message, result);
            }
        }
        return resultData;
    }
}
