package com.phoneshop.shop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.phoneshop.shop.entity.Goods;
import com.phoneshop.shop.entity.vo.QueryVo;

public interface GoodsService extends IService<Goods> {
    Page<Goods> pageGoodsList(QueryVo queryVo);
}
