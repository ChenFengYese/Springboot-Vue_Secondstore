package com.example.SecondStore.service;

import com.alipay.api.AlipayApiException;
import com.example.SecondStore.entity.AliPay;



public interface PayService {

    String aliPay(AliPay aliPayBean) throws AlipayApiException;
}
