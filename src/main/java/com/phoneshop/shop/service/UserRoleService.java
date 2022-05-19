package com.phoneshop.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.phoneshop.shop.entity.UserRole;

import java.util.List;

public interface UserRoleService extends IService<UserRole> {
    Boolean deleteUserRoleByUserId(Integer userId);
    Boolean saveUserRole(Integer userId, String roleIds);
//    List<UserRole> getUserRoleByUserId(Integer userId);
    Long getUserRoleCount(Integer userId);
}
