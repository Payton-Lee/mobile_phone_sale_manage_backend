package com.phoneshop.shop.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ps_user")
public class User {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String sex;
    private String telephone;
    private String hobby;
    private String introduce;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer state;
    private Double stature;
    private String qualification;
}
