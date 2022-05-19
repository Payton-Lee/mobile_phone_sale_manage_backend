package com.phoneshop.shop.controller;

import cn.hutool.core.lang.Dict;
import com.phoneshop.shop.entity.result.ResultData;
import com.phoneshop.shop.entity.Role;
import com.phoneshop.shop.entity.enums.PermissionCode;
import com.phoneshop.shop.entity.enums.ReturnCode;
import com.phoneshop.shop.service.PermissionService;
import com.phoneshop.shop.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/permission")
public class PermissionManageController {
    private RoleService roleService;
    private PermissionService permissionService;
    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }
    @Autowired
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }
    @GetMapping("/permissionlist")
    public Object permissionList(HttpServletRequest request) {
        if(permissionService.verifyPermission(request.getHeader("Authorization"), PermissionCode.PC101.name)) {
            return permissionService.list();
        }else{
            return ResultData.fail(ReturnCode.ACCESS_DENIED.code, ReturnCode.ACCESS_DENIED.message);
        }
    }
    @PostMapping("/newrole")
    public Object newRole(@RequestParam String role, @RequestParam String description) {
        Dict result = Dict.create();
        ResultData<Object> resultData;
        Role roleName = new Role();
        roleName.setName(role);
        roleName.setDescription(description);
        if (roleService.save(roleName)) {
            result.set("msg", "创建角色成功").set("role", roleName.getName());
            resultData = ResultData.success(ReturnCode.RC201.code, ReturnCode.RC200.message, result);
        } else {
            resultData = ResultData.fail(ReturnCode.RC999.code, ReturnCode.RC999.message);
        }
        return resultData;
    }
}
