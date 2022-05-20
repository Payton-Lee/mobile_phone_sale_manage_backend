package com.phoneshop.shop.controller;

import cn.hutool.core.lang.Dict;
import com.phoneshop.shop.entity.Goods;
import com.phoneshop.shop.entity.result.ResultData;
import com.phoneshop.shop.entity.enums.PermissionCode;
import com.phoneshop.shop.entity.enums.ReturnCode;
import com.phoneshop.shop.entity.vo.QueryVo;
import com.phoneshop.shop.service.GoodsService;
import com.phoneshop.shop.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/goods")
public class GoodsManageController {
    private GoodsService goodsService;
    private PermissionService permissionService;
    @Autowired
    protected void setGoodsService(GoodsService goodsService) {
        this.goodsService = goodsService;
    }
    @Autowired
    protected void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }


}
