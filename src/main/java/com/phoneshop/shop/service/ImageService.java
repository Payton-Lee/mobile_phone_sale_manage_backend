package com.phoneshop.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.phoneshop.shop.entity.Image;

import java.util.List;

public interface ImageService extends IService<Image> {
    List<Image> findGoodsImage(Integer goodsId);
}
