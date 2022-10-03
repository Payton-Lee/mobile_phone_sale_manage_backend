package com.phoneshop.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.phoneshop.shop.entity.Consignee;
import com.phoneshop.shop.entity.vo.UserConsigneeVo;

import java.util.List;

public interface ConsigneeService extends IService<Consignee> {
    UserConsigneeVo findUserConsigneeByConsigneeId(Integer consigneeId);
    List<UserConsigneeVo> findUserConsigneeByUserId(Integer userId);
}
