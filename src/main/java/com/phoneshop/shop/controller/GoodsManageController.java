package com.phoneshop.shop.controller;

import com.phoneshop.shop.entity.result.ResultData;
import com.phoneshop.shop.entity.enums.PermissionCode;
import com.phoneshop.shop.entity.enums.ReturnCode;
import com.phoneshop.shop.service.GoodsService;
import com.phoneshop.shop.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/goodslist")
    public Object getGoodsList(HttpServletRequest request) {
        if(permissionService.verifyPermission(request.getHeader("Authorization"), PermissionCode.PC111.name)) {
            return goodsService.list();
        } else {
            return ResultData.fail(ReturnCode.ACCESS_DENIED.code, ReturnCode.ACCESS_DENIED.message);
        }
    }
}
