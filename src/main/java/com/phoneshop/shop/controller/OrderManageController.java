package com.phoneshop.shop.controller;

import cn.hutool.core.lang.Dict;
import com.phoneshop.shop.entity.Order;
import com.phoneshop.shop.entity.enums.PermissionCode;
import com.phoneshop.shop.entity.result.ResultData;
import com.phoneshop.shop.entity.enums.ReturnCode;
import com.phoneshop.shop.service.ConsigneeService;
import com.phoneshop.shop.service.OrderService;
import com.phoneshop.shop.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/order")
public class OrderManageController {
    private OrderService orderService;
    private PermissionService permissionService;
    private ConsigneeService consigneeService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
    @Autowired
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Autowired
    public void setConsigneeService(ConsigneeService consigneeService) {
        this.consigneeService = consigneeService;
    }

    @GetMapping("/orderlist")
    public Object orderList(HttpServletRequest request, @RequestParam Integer current, @RequestParam Integer size, @RequestParam String queryInfo) {
        if(permissionService.verifyPermission(request.getHeader("Authorization"), PermissionCode.PC120.name)) {
            return orderService.pageOrderList(current, size, queryInfo);
        } else {
            return ResultData.fail(ReturnCode.ACCESS_DENIED.code, ReturnCode.ACCESS_DENIED.message);
        }
    }

    @PutMapping("/{orderId}/ordersend/{isSend}")
    public Object orderSend(HttpServletRequest request, @PathVariable Integer orderId, @PathVariable Integer isSend) {
        if(permissionService.verifyPermission(request.getHeader("Authorization"), PermissionCode.PC121.name)) {
            Dict data = Dict.create();
            Order orderInDB = orderService.getById(orderId);
            if (orderInDB.getPayStatus() != 1) {
                orderInDB.setIsSend(isSend);
                if(orderService.updateById(orderInDB)) {
                    data.set("msg", "发货成功");
                    return ResultData.success(ReturnCode.RC201.code, ReturnCode.RC201.message, data);
                } else {
                    return ResultData.fail(ReturnCode.RC999.code, ReturnCode.RC999.message);
                }
            } else {
                data.set("msg", "订单未付款");
                return ResultData.fail(ReturnCode.RC999.code, ReturnCode.RC999.message, data);
            }
        } else {
            return ResultData.fail(ReturnCode.ACCESS_DENIED.code, ReturnCode.ACCESS_DENIED.message);
        }
    }
    @GetMapping("currentconsignee")
    public Object getCurrentConsigneeById(HttpServletRequest request, @RequestParam Integer consigneeId) {
        if(permissionService.verifyPermission(request.getHeader("Authorization"), PermissionCode.PC122.name)) {
            return consigneeService.findUserConsigneeByConsigneeId(consigneeId);
        } else {
            return ResultData.fail(ReturnCode.ACCESS_DENIED.code, ReturnCode.ACCESS_DENIED.message);
        }
    }
    @GetMapping("consigneelist")
    public Object getConsigneeListByUserId(HttpServletRequest request, @RequestParam Integer userId) {
        if(permissionService.verifyPermission(request.getHeader("Authorization"), PermissionCode.PC122.name)) {
            return consigneeService.findUserConsigneeByUserId(userId);
        } else {
            return ResultData.fail(ReturnCode.ACCESS_DENIED.code, ReturnCode.ACCESS_DENIED.message);
        }
    }
    @PutMapping("{orderId}/changeconsignee/{consigneeId}")
    public Object setConsigneeChange(HttpServletRequest request, @PathVariable Integer orderId, @PathVariable Integer consigneeId) {
        if(permissionService.verifyPermission(request.getHeader("Authorization"), PermissionCode.PC122.name)) {
            Order orderInDB = orderService.getById(orderId);
            Dict data = Dict.create();
            if(orderInDB.getIsSend() == 2) {
                data.set("msg", "订单已发货，无法修改地址！");
                return ResultData.fail(ReturnCode.RC999.code, ReturnCode.RC999.message, data);
            } else {
                orderInDB.setConsigneeId(consigneeId);
                if(orderService.updateById(orderInDB)) {
                    data.set("msg", "订单地址修改成功！").set("order", orderInDB);
                    return ResultData.success(ReturnCode.RC201.code, ReturnCode.RC201.message, data);
                } else {
                    return ResultData.fail(ReturnCode.RC999.code, ReturnCode.RC999.message);
                }
            }
        } else {
            return ResultData.fail(ReturnCode.ACCESS_DENIED.code, ReturnCode.ACCESS_DENIED.message);
        }
    }
}
