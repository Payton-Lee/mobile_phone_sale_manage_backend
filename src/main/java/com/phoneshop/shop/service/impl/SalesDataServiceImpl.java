package com.phoneshop.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoneshop.shop.entity.SalesData;
import com.phoneshop.shop.mapper.SalesDataMapper;
import com.phoneshop.shop.service.SalesDataService;
import org.springframework.stereotype.Service;

@Service
public class SalesDataServiceImpl extends ServiceImpl<SalesDataMapper, SalesData> implements SalesDataService {
}
