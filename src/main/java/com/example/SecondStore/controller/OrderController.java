package com.example.SecondStore.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.SecondStore.dao.AliUpdate;
import com.example.SecondStore.dao.SellDao;
import com.example.SecondStore.entity.*;
import com.example.SecondStore.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 订单(Order)表控制层
 *
 * @author bwmgd
 * @since 2021-04-25 09:50:11
 */
@Slf4j
@RestController
@RequestMapping("order")
public class OrderController extends ApiController {
    /**
     * 服务对象
     */
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private MiddleCountService middleCountService;
    @Autowired
    private CommodityService commodityService;
    @Autowired
    AliUpdate aliUpdate;
    @Autowired
    SellDao sellDao;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.orderService.getById(id));
    }

    @GetMapping("user/{userId}")
    public R selectByUserID(@RequestParam("page") long page, @PathVariable Serializable userId) {
        return success(this.orderService.page(new Page<>(page, 20),
                new LambdaQueryWrapper<Orders>().eq(Orders::getUserId, userId)));
    }

    @GetMapping("shop/{shopId}")
    public R selectByShopID(@RequestParam("page") long page, @PathVariable Serializable shopId) {
        return success(this.orderService.page(new Page<>(page, 20),
                new LambdaQueryWrapper<Orders>().eq(Orders::getShopId, shopId).between(Orders::getStatus,2,8)));
    }

    @GetMapping
    public R selectAll(@RequestParam("page") long page){
        return success(this.orderService.pageByStatus(new Page<>(page, 20),8));
    }

    @GetMapping("transform/{username}")
    public JSONArray selectOrder(@PathVariable String username){
        System.out.println(username);

        List<transform> lst = aliUpdate.SelectOrder(username);
        return JSONArray.parseArray(JSON.toJSONString(lst));
    }

    /**
     * 修改数据
     *
     * @param orders 实体对象
     * @return 修改结果
     */
    @PostMapping("update")
    public R update(@RequestBody Orders orders) {

        if (orders.getCanReturn() == 1) {
            if (orders.getStatus() == 5 || orders.getStatus() == 6) {
                return failed("支付超过24h,无法退货");
            }
        }
        else if (orders.getStatus() == 6) {
            MiddleCount middleCount = middleCountService.getMiddleCountByOrderId(orders.getId());
            User user = userService.getById(orders.getUserId());
            user.setWallet(user.getWallet() + middleCount.getAmount());
            SimpleDateFormat sdf = new SimpleDateFormat();
            sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            String dateNow = sdf.format(date).toString();
            String description = "订单     :"+middleCount.getOrderId()+"  ,商品ID:"+middleCount.getSellerId();
            aliUpdate.MoneyUpdate(user.getName(),"+"+middleCount.getAmount(),"退货",description,dateNow);
            String sellName = aliUpdate.selectBySeId(middleCount.getSellerId());
            aliUpdate.MoneyUpdate(sellName,"+"+middleCount.getAmount(),"退货",description,dateNow);
            middleCountService.removeById(middleCount.getId());
        }
        if (orders.getStatus() == 1 || orders.getStatus() == 6) {
            Commodity commodity = commodityService.getById(orders.getCommodityId());
            commodity.setInventory(commodity.getInventory() + orders.getQuantity());
            commodityService.updateById(commodity);
        }
        return success(this.orderService.updateById(orders));
    }

    @PostMapping("pay")
    public R payOrders(@RequestBody List<Orders> orders) {
        String name__;
        String price__;

        String description;

        String dateNow;
        User user = userService.getById(orders.get(0).getUserId());
        double sum = orders.stream().mapToDouble(Orders::getAmount).sum();
        if (user.getWallet() < sum) {
            return failed("余额不足");
        }
        for (Orders order : orders) {
            MiddleCount middleCount = new MiddleCount();
            middleCount.setOrderId(order.getId());
            middleCount.setUserId(user.getId());
            middleCount.setAmount(order.getAmount());
            middleCount.setSellerId(shopService.getById(order.getShopId()).getSellerId());
            user.setWallet(user.getWallet() - order.getAmount());

            System.out.println("begin+_+______________"+user);
            name__ = user.getName();
            price__ = String.valueOf(order.getAmount());
            SimpleDateFormat sdf = new SimpleDateFormat();
            sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            dateNow = sdf.format(date).toString();
            description = "订单     :"+ order.getId()+"  ,商品ID:"+order.getCommodityId();
            aliUpdate.MoneyUpdate(name__,"-"+price__,"购买",description,dateNow);
            String sell_name = aliUpdate.selectBySid(order.getShopId());
            description = "订单     :"+ order.getId()+"  ,商品ID:"+order.getCommodityId();
            aliUpdate.MoneyUpdate(sell_name,"+"+price__,"盈利",description,dateNow);
            System.out.println("success_______________________________________________");
            order.setStatus(2);
            middleCountService.save(middleCount);
            orderService.updateById(order);


        }
        sellDao.Update__(user.getWallet(), user.getName());
        return success(userService.updateById(user));
    }
}