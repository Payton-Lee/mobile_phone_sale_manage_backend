package com.phoneshop.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.phoneshop.shop.entity.User;


public interface UserService extends IService<User> {
    User getByUserName(String userName);
    Boolean isExist(String userName);
    String getToken(User user, String key);
    String getEncryptedPassword(String password);
    Boolean verifyPassword(String password, String pw_hash);

}
