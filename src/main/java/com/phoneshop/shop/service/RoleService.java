package com.phoneshop.shop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.phoneshop.shop.entity.Role;
import com.phoneshop.shop.entity.vo.UserRoleVo;

import java.util.List;


public interface RoleService extends IService<Role> {
    List<UserRoleVo> findRoleListByUserId(Integer userId);
    Page<Role> pageRoleList(Integer current, Integer size, String queryInfo);
}
