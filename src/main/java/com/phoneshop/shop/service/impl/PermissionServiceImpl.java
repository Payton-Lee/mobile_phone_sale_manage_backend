package com.phoneshop.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoneshop.shop.entity.Permission;
import com.phoneshop.shop.entity.vo.QueryVo;
import com.phoneshop.shop.entity.vo.RolePermissionVo;
import com.phoneshop.shop.mapper.PermissionMapper;
import com.phoneshop.shop.service.PermissionService;
import com.phoneshop.shop.service.RoleService;
import com.phoneshop.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;
    private RoleService roleService;
    private UserService userService;
    @Autowired
    protected void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }
    @Autowired
    protected void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Override
    public List<RolePermissionVo> findPermissionByRoleId(Integer roleId) {
        return permissionMapper.findPermissionByRoleId(roleId);
    }

    @Override
    public Boolean verifyPermission(String token, String permission) {
        var userRoleVos = roleService.findRoleListByUserId(userService.getTokenUserId(token));
        return userRoleVos.stream().flatMap(userRoleVo -> findPermissionByRoleId(userRoleVo.getRoleId()).stream())
                .anyMatch(rolePermissionVo -> rolePermissionVo.getPermission().equals(permission));
    }

    @Override
    public Page<Permission> pagePermissionList(QueryVo queryVo) {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        Page<Permission> page = new Page<>(queryVo.getCurrent(), queryVo.getSize());
        if(!StringUtils.isEmpty(queryVo.getQueryInfo())) {
            wrapper.like("name", queryVo.getQueryInfo());
        }
        return page(page, wrapper);
    }

}
