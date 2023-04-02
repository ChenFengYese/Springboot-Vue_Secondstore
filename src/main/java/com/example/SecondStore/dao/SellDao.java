package com.example.SecondStore.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.SecondStore.entity.Sell;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 商家(Sell)表数据库访问层
 *
 * @author bwmgd
 * @since 2021-04-25 09:50:11
 */
@Mapper
public interface SellDao extends BaseMapper<Sell> {
    @Select("update sell set wallet = #{wallet} where name = #{name}")
    void Update__(Double wallet,String name);
    @Select("update sell set card = #{card},id_card_up=#{idCardUp},id_card_back=#{idCardBack},status=#{status} where name = #{name_}")
    void UpdateSellInfo(String card,String idCardUp,String idCardBack,Integer status,String name_);

}