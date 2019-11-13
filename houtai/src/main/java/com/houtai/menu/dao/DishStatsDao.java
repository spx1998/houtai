package com.houtai.menu.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface DishStatsDao {

    void addDish(int dishID);

    void deleteDish(String id);
}
