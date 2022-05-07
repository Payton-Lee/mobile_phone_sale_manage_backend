package com.phoneshop.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.phoneshop.shop.entity.Permission;
import com.phoneshop.shop.entity.RolePermissionVo;

import java.util.List;

public interface PermissionService extends IService<Permission> {
    List<RolePermissionVo> findPermissionByRoleId(Integer roleId);
}
