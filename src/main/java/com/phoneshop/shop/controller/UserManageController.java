package com.phoneshop.shop.controller;

import cn.hutool.core.lang.Dict;
import com.phoneshop.shop.entity.*;
import com.phoneshop.shop.entity.enums.PermissionCode;
import com.phoneshop.shop.entity.enums.ReturnCode;
import com.phoneshop.shop.entity.result.ResultData;
import com.phoneshop.shop.service.PermissionService;
import com.phoneshop.shop.service.RoleService;
import com.phoneshop.shop.service.UserRoleService;
import com.phoneshop.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserManageController {
    private UserService userService;
    private RoleService roleService;
    private PermissionService permissionService;
    private UserRoleService userRoleService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }
    @Autowired
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }
    @Autowired
    public void setUserRoleService(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }
    @GetMapping("/users")
    public Object users(HttpServletRequest request, @RequestParam Integer current, @RequestParam Integer size, @RequestParam String queryInfo) {
        if(permissionService.verifyPermission(request.getHeader("Authorization"), PermissionCode.PC107.name)){
            return userService.pageUserExceptPassword(current, size, queryInfo);
        } else {
            return ResultData.fail(ReturnCode.ACCESS_DENIED.code, ReturnCode.ACCESS_DENIED.message);
        }
    }
    @PutMapping("/{userId}/edituser/{state}")
    public Object updateUserState(HttpServletRequest request, @PathVariable Integer userId, @PathVariable Integer state) {
        /*
         * 判断用户权限
         * */
        if(permissionService.verifyPermission(request.getHeader("Authorization"), PermissionCode.PC108.name)){
            Dict data = Dict.create();
            User userInDB = userService.getById(userId);
            userInDB.setState(state);
            userInDB.setUpdateTime(LocalDateTime.now());
            if(userService.updateById(userInDB)) {
                data.set("user", userService.getUserExceptPassword(userInDB.getId())).set("msg", "更新用户状态成功");
                return ResultData.success(ReturnCode.RC201.code, ReturnCode.RC201.message, data);
            } else {
                data.set("msg", "更新用户状态失败");
                return ResultData.fail(ReturnCode.RC999.code, ReturnCode.RC999.message, data);
            }
        } else {
            return ResultData.fail(ReturnCode.ACCESS_DENIED.code, ReturnCode.ACCESS_DENIED.message);
        }
    }
    @GetMapping("/rolename")
    public Object getRoleName(HttpServletRequest request) {
        if(permissionService.verifyPermission(request.getHeader("Authorization"),PermissionCode.PC109.name)){
            return ResultData.success(ReturnCode.RC200.code, ReturnCode.RC200.message, roleService.list());
        } else {
            return ResultData.fail(ReturnCode.ACCESS_DENIED.code, ReturnCode.ACCESS_DENIED.message);
        }
    }
    @PutMapping("/{userId}/edituserrole/{roleIds}")
    public Object editUserRole(HttpServletRequest request, @PathVariable Integer userId, @PathVariable String roleIds) {
        if(permissionService.verifyPermission(request.getHeader("Authorization"), PermissionCode.PC110.name)) {
            Dict data = Dict.create();
            boolean flag = false;
            if (userRoleService.getUserRoleCount(userId) != 0) {
                if (userRoleService.deleteUserRoleByUserId(userId)){
                    flag = userRoleService.saveUserRole(userId, roleIds);
                }
            } else {
                flag = userRoleService.saveUserRole(userId, roleIds);
            }
            if (flag) {
                data.set("msg", "用户角色分配成功");
                return ResultData.success(ReturnCode.RC201.code, ReturnCode.RC201.message, data);
            }
            return ResultData.fail(ReturnCode.RC999.code, ReturnCode.RC999.message);
        } else {
            return ResultData.fail(ReturnCode.ACCESS_DENIED.code, ReturnCode.ACCESS_DENIED.message);
        }
    }
    @GetMapping("/userrole/{userId}")
    public Object getUserRole(HttpServletRequest request, @PathVariable Integer userId) {
        if (permissionService.verifyPermission(request.getHeader("Authorization"), PermissionCode.PC107.name)) {
            return ResultData.success(ReturnCode.RC200.code, ReturnCode.RC200.message, userService.findUserRoleByUserId(userId));
        } else {
            return ResultData.fail(ReturnCode.ACCESS_DENIED.code, ReturnCode.ACCESS_DENIED.message);
        }
    }
}
