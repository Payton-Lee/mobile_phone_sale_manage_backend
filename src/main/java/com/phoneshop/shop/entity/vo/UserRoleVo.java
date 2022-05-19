package com.phoneshop.shop.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleVo {
    private Integer roleId;
    private String username;
    private String role;
}
