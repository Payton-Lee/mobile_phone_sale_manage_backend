package com.phoneshop.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoneshop.shop.entity.Image;
import com.phoneshop.shop.mapper.ImageMapper;
import com.phoneshop.shop.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image> implements ImageService {
    private ImageMapper imageMapper;
    @Autowired
    public void setConsigneeMapper(ImageMapper imageMapper) {
        this.imageMapper = imageMapper;
    }
    @Override
    public List<Image> findGoodsImage(Integer goodsId) {
        QueryWrapper<Image> wrapper = new QueryWrapper<>();
        wrapper.eq("goods_id", goodsId);
        return imageMapper.getGoodsImage(wrapper);
    }
}
