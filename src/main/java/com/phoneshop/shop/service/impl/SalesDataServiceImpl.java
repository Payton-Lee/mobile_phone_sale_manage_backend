package com.phoneshop.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoneshop.shop.entity.SalesData;
import com.phoneshop.shop.mapper.SalesDataMapper;
import com.phoneshop.shop.service.SalesDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesDataServiceImpl extends ServiceImpl<SalesDataMapper, SalesData> implements SalesDataService {
    private SalesDataMapper salesDataMapper;
    @Autowired
    protected void setSalesDataMapper(SalesDataMapper salesDataMapper) {
        this.salesDataMapper = salesDataMapper;
    }
    @Override
    public Page<SalesData> pageSalesData(Integer current, Integer size, String queryInfo) {
        QueryWrapper<SalesData> wrapper = new QueryWrapper<>();
        Page<SalesData> page = new Page<>(current, size);
        if(!StringUtils.isEmpty(queryInfo)) {
            wrapper.like("goods", queryInfo);
        }
        return salesDataMapper.pageSalesData(page, wrapper);
    }

    @Override
    public List<SalesData> findSalesData() {
        QueryWrapper<SalesData> wrapper = new QueryWrapper<>();
        return salesDataMapper.findSalesData(wrapper);
    }
}
