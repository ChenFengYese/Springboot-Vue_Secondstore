package com.example.SecondStore.service.impl;

import com.alipay.api.AlipayApiException;
import com.example.SecondStore.config.AlipayConfig;
import com.example.SecondStore.entity.AliPay;
import com.example.SecondStore.service.PayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 支付服务接口
 */
@Service
public class PayServiceImpl implements PayService {

    /**日志对象*/
    private static final Logger logger = LoggerFactory.getLogger(PayServiceImpl.class);

    @Autowired
    private AlipayConfig alipayConfig;

    @Override
    public String aliPay(AliPay aliPayBean) throws AlipayApiException {
        logger.info("调用支付服务接口...");
        return alipayConfig.pay(aliPayBean);
    }
}