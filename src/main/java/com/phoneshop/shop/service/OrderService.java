package com.phoneshop.shop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.phoneshop.shop.entity.Order;

public interface OrderService extends IService<Order> {
    Page<Order> pageOrderList(Integer current, Integer size, String queryInfo);
}
