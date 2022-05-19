package com.phoneshop.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoneshop.shop.entity.Role;
import com.phoneshop.shop.entity.vo.UserRoleVo;
import com.phoneshop.shop.mapper.RoleMapper;
import com.phoneshop.shop.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;


    @Override
    public List<UserRoleVo> findRoleListByUserId(Integer userId) {
        return roleMapper.findRoleListByUserId(userId);
    }
}
