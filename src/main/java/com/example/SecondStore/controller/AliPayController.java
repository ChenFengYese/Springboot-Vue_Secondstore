package com.example.SecondStore.controller;

import com.alipay.api.AlipayApiException;
import com.example.SecondStore.dao.AliUpdate;
import com.example.SecondStore.entity.AliPay;
import com.example.SecondStore.entity.RequestBody__;
import com.example.SecondStore.service.PayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


@Controller
@RequestMapping("/ali")
public class AliPayController {

    /**日志对象*/
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    String name__;
    String price__;
    String subject__;

    String description;

    String dateNow;
    @Autowired
    private PayService payService;

    @Autowired
    AliUpdate aliUpdate;

    @RequestMapping("/pay")
    @ResponseBody
    public String alipay(@RequestBody RequestBody__ a) throws AlipayApiException {
        try{
            AliPay alipayBean = new AliPay();

            alipayBean.setOut_trade_no(a.getOutTradeNo());
            alipayBean.setSubject(a.getSubject());

            String __ = a.getTotalAmount().replaceAll("\"", "");
            Double i = Double.parseDouble(__);

            alipayBean.setTotal_amount(String.valueOf(i));
            subject__ = a.getSubject();
            name__ = a.getName();
            price__ = a.getTotalAmount();
            description = "订单  : "+a.getOutTradeNo() + ",  "+a.getBody();
            SimpleDateFormat sdf = new SimpleDateFormat();
            sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            dateNow = sdf.format(date).toString();
            alipayBean.setBody(a.getBody());

            return payService.aliPay(alipayBean);
        }catch (Exception e){
            System.out.println(e);
            return "订单查询失败";
        }

    }

    @RequestMapping(value = "success")
    @ResponseBody
    public String suc() throws IOException {

        if(Objects.equals(subject__, "联系客服")){
//            return "<head>\n" +
//                    "<meta http-equiv=\"refresh\" content=\"0;url=https://wpa.qq.com/msgrd?v=3&uin=3107663466&site=qq&menu=yes \">\n" +
//                    "</head>\n";
//            return "redirect:https://wpa.qq.com/msgrd?v=3&uin=3107663466&site=qq&menu=yes";
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + "https://wpa.qq.com/msgrd?v=3&uin=3107663466&site=qq&menu=yes");
            return "<head>\n" +
                    "<meta http-equiv=\"refresh\" content=\"0;url=http://localhost:8080/#/home\">\n" +
                    "</head>\n";
        }
        else{
            Double price = Double.parseDouble(price__);

            aliUpdate.aliUpdate(name__,price);
            aliUpdate.aliUpdate__(name__,price);
            aliUpdate.MoneyUpdate(name__,"+"+price.toString(),"转入",description,dateNow);

            return "<head>\n" +
                    "<meta http-equiv=\"refresh\" content=\"0;url=http://localhost:8080/#/account\">\n" +
                    "</head>\n";
        }

    }

}
