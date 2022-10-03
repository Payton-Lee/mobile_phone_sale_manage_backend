package com.phoneshop.shop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.phoneshop.shop.entity.User;
import com.phoneshop.shop.entity.vo.UserRoleVo;

import java.util.List;


public interface UserService extends IService<User> {
    User getByUserName(String userName);
    Boolean isExist(String userName);
    String getToken(User user);
    String getEncryptedPassword(String password);
    Boolean verifyPassword(String password, String pw_hash);
    Boolean register(User user);
    User getUserExceptPassword(Integer userId);
    Integer getTokenUserId(String token);
    Page<User> pageUserExceptPassword(Integer current, Integer size, String queryInfo);
    UserRoleVo findUserRoleByUserId(Integer userId);
}
