package com.example.SecondStore.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.SecondStore.entity.Commodity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 商品(Commodity)表数据库访问层
 *
 * @author bwmgd
 * @since 2021-04-25 09:50:11
 */
@Mapper
public interface CommodityDao extends BaseMapper<Commodity> {
    @Select("select se.name from commodity c,shop s,sell se where c.id = #{id} and c.shop_id = s.id and s.seller_id=se.id ")
    String selectByCid(Integer id);


}