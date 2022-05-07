package com.phoneshop.shop.controller;

import com.phoneshop.shop.entity.User;
import com.phoneshop.shop.entity.UserRoleVo;
import com.phoneshop.shop.service.RoleService;
import com.phoneshop.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserManageController {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }
    @GetMapping("/users")
    public List<User> users() {
        return userService.getUserExceptPassword();
    }
    @PutMapping("/updatestate")
    public List<UserRoleVo> updateState(HttpServletRequest request, @RequestParam(required = false) Integer id, @RequestParam(required = false) Integer state) {
        return roleService.findRoleListByUserId(userService.getTokenUserId(request.getHeader("Authorization")));
    }
}
