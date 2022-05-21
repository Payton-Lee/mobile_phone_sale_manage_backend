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
import java.time.LocalDateTime;

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
    public Object getGoodsList(HttpServletRequest request, @RequestBody QueryVo queryVo) {
        if(permissionService.verifyPermission(request.getHeader("Authorization"), PermissionCode.PC111.name)) {
            return goodsService.pageGoodsList(queryVo);
        } else {
            return ResultData.fail(ReturnCode.ACCESS_DENIED.code, ReturnCode.ACCESS_DENIED.message);
        }
    }
    @PostMapping("/newgoods")
    public Object newGoods(HttpServletRequest request, @RequestBody Goods goods) {
        if(permissionService.verifyPermission(request.getHeader("Authorization"), PermissionCode.PC112.name)) {
            Dict data = Dict.create();
            if(goodsService.save(goods)) {
                goods.setGoodsState(1);
                goods.setIsDeleted(1);
                goods.setAddTime(LocalDateTime.now());
                data.set("msg", "添加商品成功").set("goods", goods.getGoods());
                return ResultData.success(ReturnCode.RC201.code, ReturnCode.RC201.message, data);
            }
            data.set("msg", "添加商品失败").set("goods", goods.getGoods());
            return ResultData.fail(ReturnCode.RC999.code, ReturnCode.RC999.message, data);
        } else {
            return ResultData.fail(ReturnCode.ACCESS_DENIED.code, ReturnCode.ACCESS_DENIED.message);
        }
    }
    @PutMapping("/editgoods")
    public Object editGoods(HttpServletRequest request, @RequestBody Goods goods) {
        if(permissionService.verifyPermission(request.getHeader("Authorization"), PermissionCode.PC113.name)) {
            Dict data = Dict.create();
            if(goodsService.getById(goods.getId()) == null) {
                data.set("msg", "编辑的商品不存在").set("goods", goods.getGoods());
                return ResultData.fail(ReturnCode.RC999.code, ReturnCode.RC999.message, data);
            }
            if(goodsService.updateById(goods)) {
                goods.setUpdateTime(LocalDateTime.now());
                data.set("msg", "编辑商品信息成功").set("goods", goods.getGoods());
                return ResultData.success(ReturnCode.RC201.code, ReturnCode.RC201.message, data);
            }
            data.set("msg", "编辑商品信息失败").set("goods", goods.getGoods());
            return ResultData.success(ReturnCode.RC999.code, ReturnCode.RC999.message, data);
        }else {
            return ResultData.fail(ReturnCode.ACCESS_DENIED.code, ReturnCode.ACCESS_DENIED.message);
        }
    }
    @PutMapping("/onshelves")
    public Object onShelves(HttpServletRequest request, @RequestParam Integer goodsId, @RequestParam  Integer state){
        if(permissionService.verifyPermission(request.getHeader("Authorization"), PermissionCode.PC114.name)) {
            Dict data = Dict.create();
            if(goodsService.getById(goodsId) != null) {
                Goods goodsInDB = goodsService.getById(goodsId);
                goodsInDB.setUpdateTime(LocalDateTime.now());
                goodsInDB.setGoodsState(state);
                if(goodsService.updateById(goodsInDB)) {
                    data.set("msg", "商品状态修改成功").set("goods", goodsInDB.getGoods());
                    return ResultData.fail(ReturnCode.RC201.code, ReturnCode.RC201.message, data);
                }
                data.set("msg", "商品状态修改失败").set("goods", goodsInDB.getGoods());
                return ResultData.fail(ReturnCode.RC999.code, ReturnCode.RC999.message, data);
            }
            data.set("msg", "无此商品，请重试");
            return ResultData.fail(ReturnCode.RC999.code, ReturnCode.RC999.message, data);
        }else {
            return ResultData.fail(ReturnCode.ACCESS_DENIED.code, ReturnCode.ACCESS_DENIED.message);
        }
    }
    @DeleteMapping("/deletegoods")
    public Object deleteGoods(HttpServletRequest request, @RequestParam Integer goodsId) {
        if(permissionService.verifyPermission(request.getHeader("Authorization"), PermissionCode.PC115.name)) {
            Dict data = Dict.create();
            if(goodsService.getById(goodsId) != null) {
                Goods goodsInDB = goodsService.getById(goodsId);
                goodsInDB.setIsDeleted(2);
                goodsInDB.setUpdateTime(LocalDateTime.now());
                if(goodsService.updateById(goodsInDB)) {
                    data.set("msg", "商品删除成功").set("goods", goodsInDB.getGoods());
                    return ResultData.fail(ReturnCode.RC201.code, ReturnCode.RC201.message, data);
                }
                data.set("msg", "商品删除失败").set("goods", goodsInDB.getGoods());
                return ResultData.fail(ReturnCode.RC999.code, ReturnCode.RC999.message, data);
            }
            data.set("msg", "无此商品，请重试");
            return ResultData.fail(ReturnCode.RC999.code, ReturnCode.RC999.message, data);
        }else {
            return ResultData.fail(ReturnCode.ACCESS_DENIED.code, ReturnCode.ACCESS_DENIED.message);
        }
    }

}
