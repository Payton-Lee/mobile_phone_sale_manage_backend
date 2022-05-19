package com.phoneshop.shop.service;

import cn.hutool.jwt.JWTValidator;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.phoneshop.shop.entity.User;
import com.phoneshop.shop.entity.vo.QueryVo;

import java.util.List;


public interface UserService extends IService<User> {
    User getByUserName(String userName);
    Boolean isExist(String userName);
    String getToken(User user);
    String getEncryptedPassword(String password);
    Boolean verifyPassword(String password, String pw_hash);
    Boolean register(User user);
    List<User> getUserExceptPassword();
    Integer getTokenUserId(String token);
    Page<User> pageUserExceptPassword(QueryVo queryVo);
}
