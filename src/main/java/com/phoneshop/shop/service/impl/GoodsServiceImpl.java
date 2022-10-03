package com.phoneshop.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoneshop.shop.entity.Goods;
import com.phoneshop.shop.mapper.GoodsMapper;
import com.phoneshop.shop.service.GoodsService;
import org.springframework.stereotype.Service;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
    @Override
    public Page<Goods> pageGoodsList(Integer current, Integer size, String queryInfo) {
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        Page<Goods> page = new Page<>(current, size);
        if(!StringUtils.isEmpty(queryInfo)) {
            wrapper.like("goods", queryInfo);
        }
        wrapper.eq("is_deleted", 1);
        return page(page, wrapper);
    }
}
