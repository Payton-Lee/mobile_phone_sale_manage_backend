package com.phoneshop.shop;

import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.phoneshop.shop.entity.vo.RolePermissionVo;
import com.phoneshop.shop.entity.User;
import com.phoneshop.shop.entity.vo.UserRoleVo;
import com.phoneshop.shop.entity.enums.PermissionCode;
import com.phoneshop.shop.service.*;
import com.phoneshop.shop.utils.CodeGenerateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class MobilePhoneSaleApplicationTests {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private ConsigneeService consigneeService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private SalesDataService salesDataService;
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
    @Test
    void checkPermission() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwYXNzd29yZCI6IiQyYSQxMCRCYW9HZmhVeXNCYVhNWnNyTXUwaTMuWE53RUR3Q0lOSVpLMUY5SGxNR2NuUXlOMEcwWWptVyIsIm5iZiI6MTY1MjUzODQ2NiwiZXhwIjoxNjUyNTM5MDY2LCJpYXQiOjE2NTI1Mzg0NjYsInVzZXJJZCI6MywidXNlcm5hbWUiOiJ4aWFvbWluZyJ9.bIMwoqLND5L56cVteKyvfi0JoIc38op-JB4yaGOtQ8Q";
        Integer userid = userService.getTokenUserId(token);
        List<UserRoleVo> userRoleVos = roleService.findRoleListByUserId(userid);
        for (UserRoleVo userRoleVo : userRoleVos) {
            List<RolePermissionVo> rolePermissionVos = permissionService.findPermissionByRoleId(userRoleVo.getRoleId());
            for(RolePermissionVo rolePermissionVo : rolePermissionVos) {
                System.out.println(rolePermissionVo.getPermission());
            }
        }

        Boolean permission = permissionService.verifyPermission(token, PermissionCode.PC101.name);
        System.out.println(permission);
    }
    @Test
    void jsonToClass() {
        String json = "{\"username\":\"xiaozhang\",\"password\":123456}";
        User user = JSONUtil.toBean(json, User.class);
        System.out.println(user);
    }

    @Test
    void testString() {
        String roleIds = "1,2,3,4";
        String[] roleId = roleIds.split(",");
        System.out.println(Arrays.toString(roleId));
    }

    @Test
    void testConsignee() {
        System.out.println(consigneeService.findUserConsigneeByConsigneeId(1));
        System.out.println(consigneeService.findUserConsigneeByUserId(5));
    }
    @Test
    void testPrductCode() {
        System.out.println(CodeGenerateUtils.generateProductCode());
        System.out.println(CodeGenerateUtils.generateOrderSn(1));
        System.out.println(CodeGenerateUtils.generateUnionPaySn());
        System.out.println(CodeGenerateUtils.generateProductCode());
    }
    @Test
    void testImageList() {
        System.out.println(imageService.findGoodsImage(1));
    }
    @Test
    void testSalesData() {
        System.out.println(salesDataService.findSalesData());
    }
    @Test
    void testSalesDataPage() {
        System.out.println(salesDataService.pageSalesData(1, 5, ""));
    }
}
