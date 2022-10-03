package com.phoneshop.shop.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoneshop.shop.entity.UserRole;
import com.phoneshop.shop.mapper.UserRoleMapper;
import com.phoneshop.shop.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Override
    public Boolean deleteUserRoleByUserId(Integer userId) {
        QueryWrapper<UserRole> wrappers = new QueryWrapper<>();
        wrappers.eq("user_id", userId);
        return remove(wrappers);
    }

    @Override
    public Boolean saveUserRole(Integer userId, String roleIds) {
        String[] roleId = roleIds.split(",");
        boolean flag = false;
        for (String s : roleId) {
            UserRole userRole = new UserRole();
            userRole.setRoleId(Convert.toInt(s));
            userRole.setUserId(userId);
            flag = save(userRole);
        }
        return flag;
    }

    @Override
    public Long getUserRoleCount(Integer userId) {
        QueryWrapper<UserRole> wrappers = new QueryWrapper<>();
        wrappers.eq("user_id", userId);
        return count(wrappers);
    }

//    @Override
//    public List<UserRole> getUserRoleByUserId(Integer userId) {
//        return null;
//    }

}
