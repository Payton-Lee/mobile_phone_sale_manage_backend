package com.phoneshop.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoneshop.shop.entity.Goods;
import com.phoneshop.shop.entity.Role;
import com.phoneshop.shop.entity.vo.QueryVo;
import com.phoneshop.shop.mapper.GoodsMapper;
import com.phoneshop.shop.service.GoodsService;
import org.springframework.stereotype.Service;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
    @Override
    public Page<Goods> pageGoodsList(QueryVo queryVo) {
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        Page<Goods> page = new Page<>(queryVo.getCurrent(), queryVo.getSize());
        if(!StringUtils.isEmpty(queryVo.getQueryInfo())) {
            wrapper.like("goods", queryVo.getQueryInfo());
        }
        return page(page, wrapper);
    }
}
