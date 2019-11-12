package com.houtai.order.dao;

import com.houtai.order.domain.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface OrderDetailDao {

    void deleteDish(String orderId, String dishId);

    OrderDetail getOrderDetailById(String id);
}
