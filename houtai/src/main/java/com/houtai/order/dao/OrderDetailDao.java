package com.houtai.order.dao;

import com.houtai.order.domain.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface OrderDetailDao {

    void deleteDish(int id, String orderId, String dishId);

    List<OrderDetail> getOrderDetailById(String id);

    void deleteByOrderId(String id);
}
