package com.example.SecondStore.controller;


import com.example.SecondStore.dao.CheckIDMapper;
import com.example.SecondStore.entity.CheckID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "check_")
public class CheckIDController {

    @Autowired
    CheckIDMapper checkIDMapper;

    @RequestMapping(value = "id_")
    public boolean check_(@RequestBody CheckID checkID){
        String back__ = checkIDMapper.checkID(checkID.getId_());
        boolean flag_;
        flag_ = back__ == null;
        return flag_;
    }
    @RequestMapping(value = "phone_")
    public boolean check_phone(@RequestBody CheckID checkID){
        String back__ = checkIDMapper.checkPhone(checkID.getPhone_());
        boolean flag_;
        flag_ = back__ == null;
        return flag_;
    }
    @RequestMapping(value = "email_")
    public boolean check_email(@RequestBody CheckID checkID){
        String back__ = checkIDMapper.checkEmail(checkID.getEmail_());
        boolean flag_;
        flag_ = back__ == null;
        return flag_;
    }
    @RequestMapping(value = "card_")
    public boolean check_card(@RequestBody CheckID checkID){
        String back__ = checkIDMapper.checkCard(checkID.getCard_());
        boolean flag_;
        flag_ = back__ == null;
        return flag_;
    }

}
