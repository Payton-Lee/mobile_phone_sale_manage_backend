package com.phoneshop.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.phoneshop.shop.entity.RolePermission;

public interface RolePermissionService extends IService<RolePermission> {
    Boolean deleteRolePermissionByRoleId(Integer roleId);
    Long getRolePermissionCount(Integer roleId);
    Boolean saveRolePermission(Integer roleId, String permissionIds);
}
