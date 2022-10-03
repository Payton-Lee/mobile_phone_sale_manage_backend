package com.phoneshop.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoneshop.shop.entity.Consignee;
import com.phoneshop.shop.entity.vo.UserConsigneeVo;
import com.phoneshop.shop.mapper.ConsigneeMapper;
import com.phoneshop.shop.service.ConsigneeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsigneeServiceImpl extends ServiceImpl<ConsigneeMapper, Consignee> implements ConsigneeService {
    @Autowired
    private ConsigneeMapper consigneeMapper;
    @Override
    public UserConsigneeVo findUserConsigneeByConsigneeId(Integer consigneeId) {
        return consigneeMapper.findUserConsigneeByConsigneeId(consigneeId);
    }

    @Override
    public List<UserConsigneeVo> findUserConsigneeByUserId(Integer userId) {
        return consigneeMapper.findUserConsigneeByUserId(userId);
    }
}
