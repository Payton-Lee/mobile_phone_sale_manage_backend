package com.phoneshop.shop.controller;

import cn.hutool.core.lang.Dict;
import com.phoneshop.shop.entity.result.ResultData;
import com.phoneshop.shop.entity.Role;
import com.phoneshop.shop.entity.enums.PermissionCode;
import com.phoneshop.shop.entity.enums.ReturnCode;
import com.phoneshop.shop.service.PermissionService;
import com.phoneshop.shop.service.RolePermissionService;
import com.phoneshop.shop.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/permission")
public class PermissionManageController {
    private RoleService roleService;
    private PermissionService permissionService;
    private RolePermissionService rolePermissionService;
    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }
    @Autowired
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }
    @Autowired
    public void setRolePermissionService(RolePermissionService rolePermissionService) {
        this.rolePermissionService = rolePermissionService;
    }
    @GetMapping("/permissionlist")
    public Object permissionList(HttpServletRequest request, @RequestParam Integer current, @RequestParam Integer size, @RequestParam String queryInfo) {
        if(permissionService.verifyPermission(request.getHeader("Authorization"), PermissionCode.PC101.name)) {
            return permissionService.pagePermissionList(current, size, queryInfo);
        }else{
            return ResultData.fail(ReturnCode.ACCESS_DENIED.code, ReturnCode.ACCESS_DENIED.message);
        }
    }
    @GetMapping("/rolelist")
    public Object roleList(HttpServletRequest request, @RequestParam Integer current, @RequestParam Integer size, @RequestParam String queryInfo) {
        if(permissionService.verifyPermission(request.getHeader("Authorization"), PermissionCode.PC102.name)) {
            return roleService.pageRoleList(current, size, queryInfo);
        } else {
            return ResultData.fail(ReturnCode.ACCESS_DENIED.code, ReturnCode.ACCESS_DENIED.message);
        }
    }
    @PostMapping("/newrole")
    public Object newRole(HttpServletRequest request, @RequestBody Role role) {
        Dict result = Dict.create();
        if(permissionService.verifyPermission(request.getHeader("Authorization"), PermissionCode.PC103.name)) {
            if (roleService.save(role)) {
                result.set("msg", "创建角色成功").set("role", role.getName());
                return ResultData.success(ReturnCode.RC201.code, ReturnCode.RC200.message, result);
            } else {
                return ResultData.fail(ReturnCode.RC999.code, ReturnCode.RC999.message);
            }
        } else {
            return ResultData.fail(ReturnCode.ACCESS_DENIED.code, ReturnCode.ACCESS_DENIED.message);
        }
    }
    @PutMapping("/editrole")
    public Object editRole(HttpServletRequest request, @RequestBody Role role) {
        Dict data = Dict.create();
        if(permissionService.verifyPermission(request.getHeader("Authorization"), PermissionCode.PC104.name)) {
            if(roleService.updateById(role)) {
                data.set("msg", "修改角色成功").set("role", role.getName());
                return ResultData.success(ReturnCode.RC201.code, ReturnCode.RC201.message, data);
            } else {
                return ResultData.fail(ReturnCode.RC999.code, ReturnCode.RC999.message);
            }
        } else {
            return ResultData.fail(ReturnCode.ACCESS_DENIED.code, ReturnCode.ACCESS_DENIED.message);
        }
    }
    @DeleteMapping("/deleterole/{roleId}")
    public Object deleteRole(HttpServletRequest request, @PathVariable Integer roleId) {
        if(permissionService.verifyPermission(request.getHeader("Authorization"), PermissionCode.PC105.name)) {
            Dict data = Dict.create();
            Boolean flag = false;
            if(rolePermissionService.getRolePermissionCount(roleId) != 0) {
                flag = rolePermissionService.deleteRolePermissionByRoleId(roleId);
            } else {
                flag = true;
            }
            if(flag) {
                if(roleService.getById(roleId) != null) {
                    try {
                        if (roleService.removeById(roleId)){
                            data.set("msg", "删除角色成功");
                            return ResultData.success(ReturnCode.RC201.code, ReturnCode.RC201.message, data);
                        } else {
                            data.set("msg", "删除角色失败");
                            return ResultData.fail(ReturnCode.RC999.code, ReturnCode.RC999.message, data);
                        }
                    } catch(Exception e) {
                        data.set("msg", "删除角色失败");
                        return ResultData.fail(ReturnCode.RC999.code, ReturnCode.RC999.message, data);
                    }
                } else {
                    data.set("msg", "角色不存在");
                    return ResultData.fail(ReturnCode.RC999.code, ReturnCode.RC999.message, data);
                }
            } else  {
                return ResultData.fail(ReturnCode.RC999.code, ReturnCode.RC999.message);
            }
        }else {
            return ResultData.fail(ReturnCode.ACCESS_DENIED.code, ReturnCode.ACCESS_DENIED.message);
        }
    }
    @GetMapping("/rolepermissionlist")
    public  Object listRolePermission(HttpServletRequest request) {
        if(permissionService.verifyPermission(request.getHeader("Authorization"), PermissionCode.PC101.name)) {
            return ResultData.success(ReturnCode.RC200.code, ReturnCode.RC200.message, permissionService.list());
        } else {
            return ResultData.fail(ReturnCode.ACCESS_DENIED.code, ReturnCode.ACCESS_DENIED.message);
        }
    }
    @GetMapping("/singlerolepermission{roleId}")
    public Object listSingleRolePermission(HttpServletRequest request, @PathVariable Integer roleId) {
        if(permissionService.verifyPermission(request.getHeader("Authorization"), PermissionCode.PC101.name)) {
            return ResultData.success(ReturnCode.RC200.code, ReturnCode.RC200.message, permissionService.findPermissionByRoleId(roleId));
        } else {
            return ResultData.fail(ReturnCode.ACCESS_DENIED.code, ReturnCode.ACCESS_DENIED.message);
        }
    }
    @PutMapping("/{roleId}/rolepermission")
    public Object editRolePermission(HttpServletRequest request, @PathVariable Integer roleId, @RequestParam String permissionIds) {
        if(permissionService.verifyPermission(request.getHeader("Authorization"), PermissionCode.PC105.name)) {
            Dict data =  Dict.create();
            boolean flag = false;
            if(permissionIds.length() == 0) {
                flag = rolePermissionService.deleteRolePermissionByRoleId(roleId);
            } else {
                if(rolePermissionService.getRolePermissionCount(roleId) != 0) {
                    if(rolePermissionService.deleteRolePermissionByRoleId(roleId)) {
                        flag = rolePermissionService.saveRolePermission(roleId, permissionIds);
                    }
                } else {
                    flag = rolePermissionService.saveRolePermission(roleId, permissionIds);
                }
            }
            if(flag) {
                data.set("msg", "角色权限分配成功");
                return ResultData.success(ReturnCode.RC201.code, ReturnCode.RC201.message, data);
            }
            return ResultData.fail(ReturnCode.RC999.code, ReturnCode.RC999.message);
        } else {
            return ResultData.fail(ReturnCode.ACCESS_DENIED.code, ReturnCode.ACCESS_DENIED.message);
        }
    }
}
