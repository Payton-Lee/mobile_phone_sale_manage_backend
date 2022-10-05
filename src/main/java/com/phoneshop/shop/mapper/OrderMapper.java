package com.phoneshop.shop.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.phoneshop.shop.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    @Select("SELECT o.id, o.user_id AS userId, u.username, o.order_number, \n" +
            "o.goods_id AS goodsId, g.goods, o.order_price, o.order_pay, \n" +
            "o.pay_status, o.is_send, o.consignee_id, o.create_time, o.update_time \n" +
            "FROM ps_order AS o INNER JOIN ps_goods AS g ON o.goods_id = g.id LEFT JOIN ps_user AS u ON u.id = o.user_id ${ew.customSqlSegment}")
    <P extends IPage<Order>> P findOrder(P page, @Param(Constants.WRAPPER) Wrapper<Order> queryWrapper);
}

