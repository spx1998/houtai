package com.houtai.menu.dao;

import com.houtai.menu.domain.Type;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TypeDao {
    List<Type> getTypeList();

    void addType(String name);

    void deleteType(int id);
}
