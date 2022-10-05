package com.phoneshop.shop.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ps_order")
public class Order {
    private Integer id;
    private Integer userId;
    @TableField(exist = true)
    private String username;
    private String orderNumber;
    private Integer goodsId;
    @TableField(exist = false)
    private String goods;
    private double orderPrice;
    private Integer orderPay;
    private Integer payStatus;
    private Integer isSend;
    private Integer consigneeId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
