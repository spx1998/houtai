package com.houtai.order.service;

import com.houtai.order.dao.OrderDao;
import com.houtai.order.dao.OrderDetailDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    @Autowired
    OrderDao orderDao;
    @Autowired
    OrderDetailDao orderDetailDao;

    @Transactional
    public void updatePrice(String orderId,String dishId){
        orderDao.updatePrice(orderId,dishId);
        orderDetailDao.deleteDish(orderId,dishId);
    }
}
