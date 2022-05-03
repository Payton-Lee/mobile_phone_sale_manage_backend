package com.phoneshop.shop.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ps_goods")
public class Goods {
    private Integer id;
    private String goods;
    private double price;
    private Integer isStock;
    private String productNumber;
    private double grossWeight;
    private String origin;
    private String CPUModel;
    private String runningMemory;
    private String storageCard;
    private String rearCamera;
    private String frontCamera;
    private String resolution;
    private String charger;
    private String characteristic;
    private String phoneSystem;
    private String bodyMemory;
    private String goodsIntroduce;
    private Integer isDeleted;
    private LocalDateTime addTime;
    private LocalDateTime updateTime;
    private LocalDateTime deleteTime;
    private Integer goodsState;
}
