package com.phoneshop.shop.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryVo {
    private Long current;
    private Long size;
    private String queryInfo;
}
