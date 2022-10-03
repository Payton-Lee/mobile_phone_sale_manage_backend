package com.phoneshop.shop.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.phoneshop.shop.entity.Image;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ImageMapper extends BaseMapper<Image> {
    @Select("SELECT i.id AS id, g.goods, i.image, i.type, i.numerical_order FROM ps_goods_image AS i " +
            "LEFT JOIN ps_goods AS g ON g.id = i.goods_id ${ew.customSqlSegment}")
    List<Image> getGoodsImage(@Param(Constants.WRAPPER) Wrapper<Image> queryWrapper);

    @Select("SELECT i.id AS id, g.goods, i.image, i.type, i.numerical_order FROM ps_goods_image AS i " +
            "LEFT JOIN ps_goods AS g ON g.id = i.goods_id ${ew.customSqlSegment}")
    <P extends IPage<Image>> P findGoodsImage(P page, @Param(Constants.WRAPPER) Wrapper<Image> queryWrapper);
}
