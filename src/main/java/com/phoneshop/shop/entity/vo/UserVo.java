package com.phoneshop.shop.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {
    private Integer id;
    private String username;
    private String email;
    private String sex;
    private String telephone;
    private String hobby;
    private String introduce;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer state;
}
