package com.houtai.order.controller;

import com.google.gson.Gson;
import com.houtai.common.domain.Msg;
import com.houtai.menu.dao.DishDao;
import com.houtai.menu.domain.Dish;
import com.houtai.order.dao.OrderDao;
import com.houtai.order.dao.OrderDetailDao;
import com.houtai.order.domain.Order;
import com.houtai.order.domain.OrderDetail;
import com.houtai.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

//TODO:需要一些定时任务，例如限量菜品 ；生成单号、member_id时候要建工具类
@RestController
public class OrderController {
    private Gson g = new Gson();
    @Autowired
    DishDao dishDao;
    @Autowired
    OrderDao orderDao;
    @Autowired
    OrderDetailDao orderDetailDao;
    @Autowired
    OrderService orderService;
    /**
     * 获取订单列表
     */
    @GetMapping("/order/list")
    public String getOrderList(){
        Msg m = new Msg();
        try {
            List<Order> list = orderDao.getOrderList();
            m.setStatus("ok");
            m.setContent(g.toJson(list));
        }catch (Exception e){
            e.printStackTrace();
            m.setStatus("error");
        }
        return g.toJson(m);
    }

    /**
     * 获取订单详情
     */
    @GetMapping("/order/{id}")
    public String getOrderDetail(@PathVariable("id")String id){
        Msg m = new Msg();
        try {
            List<OrderDetail> orderDetail = orderDetailDao.getOrderDetailById(id);
            List<Dish> dishes = dishDao.getDishList();
            HashMap<Integer,String> dishMap = new HashMap<>();
            for(Dish dish:dishes){
                dishMap.put(dish.getDishID(),dish.getName());
            }
            for(OrderDetail od:orderDetail){
                od.setDishName(dishMap.get(od.getDishId()));
            }
            m.setStatus("ok");
            m.setContent(g.toJson(orderDetail));
        }catch (Exception e){
            e.printStackTrace();
            m.setStatus("error");
        }
        return g.toJson(m);
    }

    /**
     * 修改订单
     */
    @PostMapping("/order/change")
    public String changeOrder(@RequestParam("id")int id,@RequestParam("orderId")String orderId,@RequestParam("dishId")String dishId){
        Msg m = new Msg();
        try {

            //TODO: 删除该菜品，重新计算价格 @transcation 是否生效需测试
           orderService.updatePrice(id,orderId,dishId);
            m.setStatus("ok");
        }catch (Exception e){
            e.printStackTrace();
            m.setStatus("error");
        }
        return g.toJson(m);
    }

    /**
     * 删除订单
     */
    @PostMapping("/order/delete")
    public String deleteOrder(@RequestParam("id")String id){
        Msg m = new Msg();
        try {
            orderService.deleteOrderById(id);
            m.setStatus("ok");
        }catch (Exception e){
            e.printStackTrace();
            m.setStatus("error");
        }
        return g.toJson(m);
    }
}
