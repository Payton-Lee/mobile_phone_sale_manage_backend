package com.phoneshop.shop.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoneshop.shop.entity.User;
import com.phoneshop.shop.entity.vo.QueryVo;
import com.phoneshop.shop.mapper.UserMapper;
import com.phoneshop.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getByUserName(String userName) {
        return getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, userName),false);
    }
    @Override
    public Boolean isExist(String userName) {
        return getByUserName(userName) != null;
    }
    @Override
    public String getToken(User user) {
        DateTime now = DateTime.now();
        DateTime newTime = now.offsetNew(DateField.MINUTE, 10);
        Map<String, Object> payload = new HashMap<>();
        // 签发时间
        payload.put(JWTPayload.ISSUED_AT, now);
        // 过期时间
        payload.put(JWTPayload.EXPIRES_AT, newTime);
        // 生效时间
        payload.put(JWTPayload.NOT_BEFORE, now);
        // 载荷
        payload.put("userId", user.getId());
        payload.put("username", user.getUsername());
        payload.put("password", user.getPassword());
        return JWTUtil.createToken(payload, "peytonlee".getBytes());
    }

    @Override
    public String getEncryptedPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Override
    public Boolean verifyPassword(String password, String pw_hash) {
        return BCrypt.checkpw(password,pw_hash);
    }

    @Override
    public Boolean register(User user) {
        return save(user);
    }

    @Override
    public List<User> getUserExceptPassword() {
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery().select(User.class, info -> !info.getColumn().equals("password"));
        return userMapper.selectList(wrapper);
    }

    @Override
    public Page<User> pageUserExceptPassword(QueryVo queryVo) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        Page<User> page = new Page<>(queryVo.getCurrent(), queryVo.getSize());
        if(!StringUtils.isEmpty(queryVo.getQueryInfo())) {
               wrapper.like("username", queryVo.getQueryInfo());
        }
        wrapper.select(User.class, info -> !info.getColumn().equals("password"));
        return page(page, wrapper);
    }

    @Override
    public Integer getTokenUserId(String token) {
        final JWT jwt = JWTUtil.parseToken(token);
        return (Integer) jwt.getPayload("userId");
    }
}
