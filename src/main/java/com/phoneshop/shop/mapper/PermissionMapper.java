package com.phoneshop.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phoneshop.shop.entity.Permission;
import com.phoneshop.shop.entity.vo.RolePermissionVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
    @Select("SELECT \n" +
            "ps_permission.id AS permissionId, \n" +
            "ps_permission.`name` AS permission, \n" +
            "ps_role.`name` AS role, \n" +
            "ps_permission.type AS type \n" +
            "FROM \n" +
            "ps_permission, ps_role, ps_role_premission \n" +
            "WHERE\n" +
            "ps_role.id = ps_role_premission.role_id \n" +
            "AND\n" +
            "ps_permission.id = ps_role_premission.permission_id \n" +
            "AND\n" +
            "ps_role.id = #{roleId};")
    List<RolePermissionVo> findPermissionByRoleId(Integer roleId);
}
