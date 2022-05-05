package com.phoneshop.shop;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.phoneshop.shop.entity.User;
import com.phoneshop.shop.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MobilePhoneSaleApplicationTests {
    @Autowired
    private UserService userService;
    @Test
    void contextLoads() {
        String password = "12345678";
        String pw_hash = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println(pw_hash);
        System.out.println(BCrypt.checkpw(password,pw_hash));
    }
    @Test
    void testJWT () {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("12345678");
        String token = userService.getToken(user, "phone_sale");
        System.out.println(token);
        verifyToken(token);
    }
    void verifyToken(String token) {
        String key = "phone_sale";
        JWT jwt = JWTUtil.parseToken(token);
        boolean verifyKey = jwt.setKey(key.getBytes()).verify();
        boolean verifyTime = jwt.validate(0);

        System.out.println(verifyKey);
        System.out.println(verifyTime);
    }
    @Test
    void testQueryUsername() {
        System.out.println(userService.getByUserName("admin"));
    }


}
