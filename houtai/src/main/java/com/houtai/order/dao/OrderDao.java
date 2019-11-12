package com.houtai.order.dao;


import com.houtai.order.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface OrderDao {
    List<Order> getOrderList();

    void updatePrice(String orderId, String dishId);
}
