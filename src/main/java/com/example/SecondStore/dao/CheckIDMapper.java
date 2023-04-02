package com.example.SecondStore.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CheckIDMapper {

    @Select("select name from secondstore.user where name = #{id_}")
    String checkID(String id_);

    @Select("select phone from secondstore.user where phone = #{phone_}")
    String checkPhone(String phone_);

    @Select("select email from secondstore.user where email = #{email_}")
    String checkEmail(String email_);

    @Select("select card from secondstore.user where card = #{card_}")
    String checkCard(String card_);
}
