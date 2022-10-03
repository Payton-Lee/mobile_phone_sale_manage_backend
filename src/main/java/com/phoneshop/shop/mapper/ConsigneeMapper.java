package com.phoneshop.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phoneshop.shop.entity.Consignee;
import com.phoneshop.shop.entity.vo.UserConsigneeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ConsigneeMapper extends BaseMapper<Consignee> {
    @Select("SELECT ps_consignee.id AS id, \n" +
            "ps_consignee.`name` AS receiver, \n" +
            "ps_consignee.telephone, \n" +
            "province.`name` AS province, \n" +
            "city.`name` AS city, \n" +
            "area.`name` AS area, \n" +
            "ps_consignee.address \n" +
            "FROM\n" +
            "ps_consignee, \n" +
            "province, \n" +
            "city, \n" +
            "area \n" +
            "WHERE \n" +
            "area.`code` = ps_consignee.area \n" +
            "AND\n" +
            "city.`code` = area.cityCode \n" +
            "AND \n" +
            "province.`code` = area.provinceCode\n" +
            "AND \n" +
            "ps_consignee.id = #{consigneeId};")
    UserConsigneeVo findUserConsigneeByConsigneeId(Integer consigneeId);

    @Select("SELECT ps_consignee.id AS id, \n" +
            "ps_consignee.`name` AS receiver, \n" +
            "ps_consignee.telephone, \n" +
            "province.`name` AS province, \n" +
            "city.`name` AS city, \n" +
            "area.`name` AS area, \n" +
            "ps_consignee.address \n" +
            "FROM\n" +
            "ps_consignee, \n" +
            "province, \n" +
            "city, \n" +
            "area \n" +
            "WHERE \n" +
            "area.`code` = ps_consignee.area \n" +
            "AND\n" +
            "city.`code` = area.cityCode \n" +
            "AND \n" +
            "province.`code` = area.provinceCode\n" +
            "AND \n" +
            "ps_consignee.user_id = #{userId};")
    List<UserConsigneeVo> findUserConsigneeByUserId(Integer userId);
}
