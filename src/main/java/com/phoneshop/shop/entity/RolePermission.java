package com.phoneshop.shop.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("ps_role_premission")
public class RolePermission {
    private Integer id;
    private Integer roleId;
    private Integer permissionId;
}
