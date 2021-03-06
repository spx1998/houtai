package com.houtai.menu.dao;

import com.houtai.menu.domain.Dish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DishDao {
    List<Dish> getDishList();

    Dish getDishById(int id);

    void addDish(String name, String introduce, String url, int price, int vPrice, int type);

    void deleteDish(String id);

    void setPrice(int id, int price, int vPrice);

    void setMaxQuantity(int id, int maxQuantity);

    void setStatus(int id, int status);

    void updateDish(@Param("dish")Dish dish);
}
