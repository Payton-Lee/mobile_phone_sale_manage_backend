package com.phoneshop.shop.controller;

import cn.hutool.core.lang.Dict;
import com.phoneshop.shop.entity.Order;
import com.phoneshop.shop.entity.result.ResultData;
import com.phoneshop.shop.entity.enums.ReturnCode;
import com.phoneshop.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderManageController {
    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @PutMapping("/ordersend")
    public Object orderSend(@RequestParam Integer orderId, @RequestParam Integer isSend) {
        Dict data = Dict.create();
        ResultData<Object> resultData;
        Order orderInDB = new Order();
        orderInDB = orderService.getById(orderId);
        if (orderInDB.getOrderPay() != 1) {
            orderInDB.setIsSend(isSend);
            if(orderService.updateById(orderInDB)) {
                data.set("msg", "发货成功");
                resultData = ResultData.success(ReturnCode.RC201.code, ReturnCode.RC201.message, data);
            } else  {
                data.set("msg", "操作失败");
                resultData = ResultData.success(ReturnCode.RC999.code, ReturnCode.RC999.message, data);
            }
        } else {
            data.set("msg", "订单未付款");
            resultData = ResultData.success(ReturnCode.RC999.code, ReturnCode.RC999.message, data);
        }
        return resultData;
    }
}
