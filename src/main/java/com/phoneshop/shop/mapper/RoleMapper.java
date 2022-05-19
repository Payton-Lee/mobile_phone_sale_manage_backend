package com.phoneshop.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phoneshop.shop.entity.Role;
import com.phoneshop.shop.entity.vo.UserRoleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    @Select("SELECT \n" +
            "ps_role.id AS roleId, \n" +
            "ps_user.username, \n" +
            "ps_role.`name` AS role \n" +
            "FROM \n" +
            "ps_user, ps_role, ps_user_role \n" +
            "WHERE \n" +
            "ps_user.id = ps_user_role.user_id \n" +
            "AND \n" +
            "ps_role.id = ps_user_role.role_id \n" +
            "AND \n" +
            "ps_user.id = #{userId};")
    List<UserRoleVo> findRoleListByUserId(Integer userId);
}
