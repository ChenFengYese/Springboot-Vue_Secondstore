package com.example.SecondStore.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.SecondStore.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 普通用户(User)表数据库访问层
 *
 * @author bwmgd
 * @since 2021-04-25 09:50:11
 */
@Mapper
public interface UserDao extends BaseMapper<User> {

    @Select("update user set wallet = #{wallet} where name = #{name}")
    void Update__(Double wallet,String name);

    @Select("update user set password = #{password_} where email = #{email_}")
    void UpdatePwd(String password_,String email_);
    @Select("update sell set password = #{password_} where name = #{name_}")
    void UpdateSellPwd(String password_,String name_);

    @Select("update user set city = #{city_}, card = #{card_} where name = #{name_}")
    void UpdateUserInfo(String city_,String card_,String name_);

    @Select("select name from user where email = #{email_}")
    String selectNameByEmail(String email_);


}