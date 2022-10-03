package com.phoneshop.shop.controller;

import cn.hutool.core.lang.Dict;
import com.phoneshop.shop.entity.Goods;
import com.phoneshop.shop.entity.Image;
import com.phoneshop.shop.entity.result.ResultData;
import com.phoneshop.shop.entity.enums.PermissionCode;
import com.phoneshop.shop.entity.enums.ReturnCode;
import com.phoneshop.shop.service.GoodsService;
import com.phoneshop.shop.service.ImageService;
import com.phoneshop.shop.service.PermissionService;
import com.phoneshop.shop.service.SalesDataService;
import com.phoneshop.shop.utils.CodeGenerateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/goods")
public class GoodsManageController {
    private GoodsService goodsService;
    private PermissionService permissionService;
    private ImageService imageService;
    private SalesDataService salesDataService;
    @Autowired
    protected void setGoodsService(GoodsService goodsService) {
        this.goodsService = goodsService;
    }
    @Autowired
    protected void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }
    @Autowired
    protected void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }
    @Autowired
    protected void setSalesDataService(SalesDataService salesDataService) {
        this.salesDataService = salesDataService;
    }
    @GetMapping("/goodslist")
    public Object getGoodsList(HttpServletRequest request, @RequestParam Integer current, @RequestParam Integer size, @RequestParam String queryInfo) {
        if(permissionService.verifyPermission(request.getHeader("Authorization"), PermissionCode.PC111.name)) {
            return goodsService.pageGoodsList(current, size, queryInfo);
        } else {
            return ResultData.fail(ReturnCode.ACCESS_DENIED.code, ReturnCode.ACCESS_DENIED.message);
        }
    }
    @PostMapping("/newgoods")
    public Object newGoods(HttpServletRequest request, @RequestBody Goods goods) {
        if(permissionService.verifyPermission(request.getHeader("Authorization"), PermissionCode.PC112.name)) {
            Dict data = Dict.create();
            goods.setProductNumber(CodeGenerateUtils.generateProductCode());
            goods.setGoodsState(1);
            goods.setIsDeleted(1);
            goods.setAddTime(LocalDateTime.now());
            if(goodsService.save(goods)) {
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
    @PutMapping("/{goodsId}/onshelves/{goodsState}")
    public Object onShelves(HttpServletRequest request, @PathVariable Integer goodsId, @PathVariable  Integer goodsState){
        if(permissionService.verifyPermission(request.getHeader("Authorization"), PermissionCode.PC114.name)) {
            Dict data = Dict.create();
            Goods goodsInDB = goodsService.getById(goodsId);
            if(goodsInDB != null) {
                goodsInDB.setUpdateTime(LocalDateTime.now());
                goodsInDB.setGoodsState(goodsState);
                if(goodsService.updateById(goodsInDB)) {
                    data.set("msg", "商品状态修改成功").set("goods", goodsInDB.getGoods());
                    return ResultData.fail(ReturnCode.RC201.code, ReturnCode.RC201.message, data);
                }
                data.set("msg", "商品状态修改失败").set("goods", goodsInDB.getGoods());
                return ResultData.fail(ReturnCode.RC999.code, ReturnCode.RC999.message, data);
            }
            data.set("msg", "无此商品");
            return ResultData.fail(ReturnCode.RC999.code, ReturnCode.RC999.message, data);
        }else {
            return ResultData.fail(ReturnCode.ACCESS_DENIED.code, ReturnCode.ACCESS_DENIED.message);
        }
    }
    @DeleteMapping("/{goodsId}/deletegoods")
    public Object deleteGoods(HttpServletRequest request, @PathVariable Integer goodsId) {
        if(permissionService.verifyPermission(request.getHeader("Authorization"), PermissionCode.PC115.name)) {
            Dict data = Dict.create();
            if(goodsService.getById(goodsId) != null) {
                Goods goodsInDB = goodsService.getById(goodsId);
                goodsInDB.setIsDeleted(2);
                goodsInDB.setDeleteTime(LocalDateTime.now());
                if(goodsService.updateById(goodsInDB)) {
                    data.set("msg", "商品删除成功").set("goods", goodsInDB.getGoods());
                    return ResultData.fail(ReturnCode.RC201.code, ReturnCode.RC201.message, data);
                } else {
                    data.set("msg", "商品删除失败").set("goods", goodsInDB.getGoods());
                    return ResultData.fail(ReturnCode.RC999.code, ReturnCode.RC999.message, data);
                }
            }
            data.set("msg", "无此商品，请重试");
            return ResultData.fail(ReturnCode.RC999.code, ReturnCode.RC999.message, data);
        }else {
            return ResultData.fail(ReturnCode.ACCESS_DENIED.code, ReturnCode.ACCESS_DENIED.message);
        }
    }
    @GetMapping("/{goodsId}/imagelist")
    public Object getImageList(HttpServletRequest request, @PathVariable Integer goodsId) {
        if(permissionService.verifyPermission(request.getHeader("Authorization"), PermissionCode.PC116.name)) {
            return imageService.findGoodsImage(goodsId);
        }else {
            return ResultData.fail(ReturnCode.ACCESS_DENIED.code, ReturnCode.ACCESS_DENIED.message);
        }
    }
    @PostMapping("/image")
    public Object uploadImage(HttpServletRequest request, @RequestParam MultipartFile file) throws IOException {
        if(permissionService.verifyPermission(request.getHeader("Authorization"), PermissionCode.PC117.name)) {
            Path path = Paths.get("image");
            if (!Files.exists(path)) {
                try {
                    Files.createDirectory(path);
                } catch (IOException e) {
                    throw new IOException("Failed to create directory:" + path, e);
                }
            }
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Path filePath = path.resolve(fileName);
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                return ResultData.success(ReturnCode.RC201.code, ReturnCode.RC201.message, fileName);
            } catch (IOException e) {
                throw new IOException("Failed to save image file:" + fileName, e);
            }
        }else {
            return ResultData.fail(ReturnCode.ACCESS_DENIED.code, ReturnCode.ACCESS_DENIED.message);
        }
    }
    @PostMapping("/newimage")
    public Object newImage(HttpServletRequest request, @RequestBody Image image) {
        if(permissionService.verifyPermission(request.getHeader("Authorization"), PermissionCode.PC117.name)) {
            Dict data = Dict.create();
            if (imageService.save(image)) {
                data.set("msg", "上传图片信息成功").set("image", image.getImage());
                return ResultData.success(ReturnCode.RC201.code, ReturnCode.RC200.message, data);
            } else {
                return ResultData.fail(ReturnCode.RC999.code, ReturnCode.RC999.message);
            }
        }else {
            return ResultData.fail(ReturnCode.ACCESS_DENIED.code, ReturnCode.ACCESS_DENIED.message);
        }
    }
    @PutMapping("/editimage")
    public Object editImage(HttpServletRequest request, @RequestBody Image image) {
        if(permissionService.verifyPermission(request.getHeader("Authorization"), PermissionCode.PC118.name)) {
            Image imageInDB = imageService.getById(image.getId());
            Dict data = Dict.create();
            if(imageInDB != null) {
                imageInDB.setType(image.getType());
                if(imageService.updateById(imageInDB)){
                    data.set("msg", "编辑图片信息成功");
                    return ResultData.success(ReturnCode.RC201.code, ReturnCode.RC201.message, data);
                }
                data.set("msg", "编辑图片信息失败");
                return ResultData.success(ReturnCode.RC999.code, ReturnCode.RC999.message, data);
            }
            data.set("msg", "图片不存在");
            return ResultData.success(ReturnCode.RC999.code, ReturnCode.RC999.message, data);
        }else {
            return ResultData.fail(ReturnCode.ACCESS_DENIED.code, ReturnCode.ACCESS_DENIED.message);
        }
    }
    @DeleteMapping("/{imageId}/deleteimage")
    public Object deleteImage(HttpServletRequest request, @PathVariable Integer imageId) {
        if(permissionService.verifyPermission(request.getHeader("Authorization"), PermissionCode.PC119.name)) {
            Image imageDB = imageService.getById(imageId);
            Dict data = Dict.create();
            if(imageDB != null) {
                if(imageService.removeById(imageId)){
                    data.set("msg", "商品图片删除成功");
                    return ResultData.success(ReturnCode.RC201.code, ReturnCode.RC201.message, data);
                }
                data.set("msg", "商品图片删除失败");
                return ResultData.fail(ReturnCode.RC999.code, ReturnCode.RC999.message, data);
            } else {
                data.set("msg", "所删除的商品图片不存在");
                return ResultData.fail(ReturnCode.RC999.code, ReturnCode.RC999.message, data);
            }
        }else {
            return ResultData.fail(ReturnCode.ACCESS_DENIED.code, ReturnCode.ACCESS_DENIED.message);
        }
    }
    @GetMapping("/pagesalesdata")
    public Object pageSalesData(HttpServletRequest request, @RequestParam Integer current, @RequestParam Integer size, @RequestParam String queryInfo) {
        if(permissionService.verifyPermission(request.getHeader("Authorization"), PermissionCode.PC123.name)) {
            return salesDataService.pageSalesData(current, size, queryInfo);
        }else {
            return ResultData.fail(ReturnCode.ACCESS_DENIED.code, ReturnCode.ACCESS_DENIED.message);
        }
    }
    @GetMapping("/getsalesdata")
    public Object getSalesData(HttpServletRequest request) {
        if(permissionService.verifyPermission(request.getHeader("Authorization"), PermissionCode.PC123.name)) {
            return salesDataService.findSalesData();
        }else {
            return ResultData.fail(ReturnCode.ACCESS_DENIED.code, ReturnCode.ACCESS_DENIED.message);
        }
    }
}
