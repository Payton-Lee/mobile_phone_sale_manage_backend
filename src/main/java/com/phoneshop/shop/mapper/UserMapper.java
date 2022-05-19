package com.phoneshop.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phoneshop.shop.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
