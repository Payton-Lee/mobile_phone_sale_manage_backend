package com.phoneshop.shop.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionVo {
    private Integer permissionId;
    private String permission;
    private String role;
}
