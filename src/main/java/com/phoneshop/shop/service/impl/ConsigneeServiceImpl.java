package com.phoneshop.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoneshop.shop.entity.Consignee;
import com.phoneshop.shop.mapper.ConsigneeMapper;
import com.phoneshop.shop.service.ConsigneeService;
import org.springframework.stereotype.Service;

@Service
public class ConsigneeServiceImpl extends ServiceImpl<ConsigneeMapper, Consignee> implements ConsigneeService {
}
