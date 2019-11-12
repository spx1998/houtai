package com.houtai.dtable.dao;

import com.houtai.dtable.domain.DTable;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface DTableDao {
    void addNewTable(int num);

    void cancelTable(int id);

    void resumeTable(int id);

    List<DTable> getTables();
}
