package com.phoneshop.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoneshop.shop.entity.Role;
import com.phoneshop.shop.entity.vo.QueryVo;
import com.phoneshop.shop.entity.vo.UserRoleVo;
import com.phoneshop.shop.mapper.RoleMapper;
import com.phoneshop.shop.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;


    @Override
    public List<UserRoleVo> findRoleListByUserId(Integer userId) {
        return roleMapper.findRoleListByUserId(userId);
    }

    @Override
    public Page<Role> pageRoleList(QueryVo queryVo) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        Page<Role> page = new Page<>(queryVo.getCurrent(), queryVo.getSize());
        if(!StringUtils.isEmpty(queryVo.getQueryInfo())) {
            wrapper.like("name", queryVo.getQueryInfo());
        }
        return page(page, wrapper);
    }
}
