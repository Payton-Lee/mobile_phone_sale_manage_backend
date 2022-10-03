package com.phoneshop.shop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.phoneshop.shop.entity.SalesData;

import java.util.List;

public interface SalesDataService extends IService<SalesData> {
    Page<SalesData> pageSalesData(Integer current, Integer size, String queryInfo);
    List<SalesData> findSalesData();
}
