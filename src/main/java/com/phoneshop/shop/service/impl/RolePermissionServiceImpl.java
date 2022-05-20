package com.phoneshop.shop.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoneshop.shop.entity.RolePermission;
import com.phoneshop.shop.mapper.RolePermissionMapper;
import com.phoneshop.shop.service.RolePermissionService;
import org.springframework.stereotype.Service;

@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {
    @Override
    public Boolean deleteRolePermissionByRoleId(Integer roleId) {
        QueryWrapper<RolePermission> wrappers = new QueryWrapper<>();
        wrappers.eq("role_id", roleId);
        return remove(wrappers);
    }

    @Override
    public Long getRolePermissionCount(Integer roleId) {
        QueryWrapper<RolePermission> wrappers = new QueryWrapper<>();
        wrappers.eq("role_id", roleId);
        return count(wrappers);
    }

    @Override
    public Boolean saveRolePermission(Integer roleId, String permissionIds) {
        String[] permissionId = permissionIds.split(",");
        boolean flag = false;
        for(String s : permissionId) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(Convert.toInt(s));
            flag = save(rolePermission);
        }
        return flag;
    }
}
