package com.phoneshop.shop.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ps_sales_data")
public class SalesData {
    private Integer id;
    private Integer goodsId;
    private Integer count;
}
