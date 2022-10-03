package com.phoneshop.shop.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.phoneshop.shop.entity.SalesData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SalesDataMapper extends BaseMapper<SalesData> {
    @Select("SELECT sd.id, g.goods, sd.count FROM ps_sales_data AS sd " +
            "LEFT JOIN ps_goods AS g ON sd.goods_id = g.id ${ew.customSqlSegment}")
    List<SalesData> findSalesData(@Param(Constants.WRAPPER) Wrapper<SalesData> queryWrapper);
    @Select("SELECT sd.id, g.goods, sd.count FROM ps_sales_data AS sd " +
            "LEFT JOIN ps_goods AS g ON sd.goods_id = g.id ${ew.customSqlSegment}")
    <P extends IPage<SalesData>> P pageSalesData(P page, @Param(Constants.WRAPPER) Wrapper<SalesData> queryWrapper);
}
