package com.phoneshop.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoneshop.shop.entity.Order;
import com.phoneshop.shop.mapper.OrderMapper;
import com.phoneshop.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    private OrderMapper orderMapper;

    @Autowired
    protected void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    public Page<Order> pageOrderList(Integer current, Integer size, String queryInfo) {

        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        Page<Order> page = new Page<>(current, size);
        if(!StringUtils.isEmpty(queryInfo)) {
            wrapper.like("goods", queryInfo);
        }
        return orderMapper.findOrder(page, wrapper);
    }
}
