package com.example.SecondStore.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.example.SecondStore.entity.transform;

import java.util.List;

@Mapper
public interface AliUpdate {

    @Select("select se.name from shop s,sell se where s.id = #{id} and s.seller_id=se.id ")
    String selectBySid(Integer id);

    @Select("select name from sell where id = #{id} ")
    String selectBySeId(Integer id);

    @Select("update user set wallet=wallet+#{price} where name=#{name}")
    public void aliUpdate(String name, Double price);
    @Select("update sell set wallet=(wallet+#{price}) where name=#{name}")
    public void aliUpdate__(String name, Double price);


    @Select("insert into transform (username,transf,Instyle,descriptions,TimeNow) values (#{name},#{price},#{style},#{description},#{dateNow})")
    public void MoneyUpdate(String name, String price,String style,String description,String dateNow);

    @Select("select username,transf,Instyle,descriptions,TimeNow from transform where username=#{username}")
    public List<transform> SelectOrder(String username);




}
