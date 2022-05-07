package com.phoneshop.shop;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.phoneshop.shop.entity.RolePermissionVo;
import com.phoneshop.shop.entity.User;
import com.phoneshop.shop.entity.UserRoleVo;
import com.phoneshop.shop.service.PermissionService;
import com.phoneshop.shop.service.RoleService;
import com.phoneshop.shop.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MobilePhoneSaleApplicationTests {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
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
        String token = userService.getToken(user);
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
    @Test
    void testListUser() {
        List<User> list = userService.list();
        for(User user : list) {
            System.out.println(user);
        }
    }

    @Test
    void findUserRole() {
        List<UserRoleVo> list = roleService.findRoleListByUserId(1);
        for (UserRoleVo userRoleVo : list) {
            System.out.println(userRoleVo);
        }
    }
    @Test
    void findRolePermission() {
        List<RolePermissionVo> list = permissionService.findPermissionByRoleId(1);
        for(RolePermissionVo rolePermissionVo : list) {
            System.out.println(rolePermissionVo);
        }
    }
    @Test
    void getTokenUserId() {
        User user = new User();
        user.setId(1);
        user.setUsername("admin");
        user.setPassword("12345678");
        String token = userService.getToken(user);
        Integer tokenUserId = userService.getTokenUserId(token);
        System.out.println(roleService.findRoleListByUserId(tokenUserId));
    }


}
