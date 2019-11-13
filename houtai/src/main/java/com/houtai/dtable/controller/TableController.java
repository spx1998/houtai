package com.houtai.dtable.controller;

import com.google.gson.Gson;
import com.houtai.common.domain.Msg;
import com.houtai.dtable.dao.DTableDao;
import com.houtai.dtable.domain.DTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TableController {
    @Autowired
    DTableDao dTableDao;

    Gson g = new Gson();

    /**
     * 新增餐桌
     */
    @PostMapping("/table/add")
    public String addTable(@RequestParam("number")int num){
        Msg m = new Msg();
        try {
            dTableDao.addNewTable(num);
            m.setStatus("ok");
        }catch (Exception e){
            e.printStackTrace();
            m.setStatus("error");
        }
        return g.toJson(m);
    }

    /**
     *  停用餐桌
     */
    @PostMapping("/table/cancel")
    public String cancelTable(@RequestParam("id")int id){
        Msg m = new Msg();
        try {
            dTableDao.cancelTable(id);
            m.setStatus("ok");

        }catch (Exception e){
            e.printStackTrace();
            m.setStatus("error");
        }
        return g.toJson(m);
    }

    /**
     * 餐桌重新投入使用
     */
    @PostMapping("/table/resume")
    public String resumeTable(@RequestParam("id")int id){
        Msg m = new Msg();
        try {
            dTableDao.resumeTable(id);
            m.setStatus("ok");
        }catch (Exception e){
            e.printStackTrace();
            m.setStatus("error");
        }
        return g.toJson(m);
    }

    /**
     * 获取餐桌列表
     */
    @GetMapping("/table/list")
    public String getTableList(){
        Msg m = new Msg();
        try {
            List<DTable> tables = dTableDao.getTables();
            m.setStatus("ok");
            m.setContent(g.toJson(tables));
        }catch (Exception e){
            e.printStackTrace();
            m.setStatus("error");
            m.setContent("获取餐桌列表失败");
        }
        return g.toJson(m);
    }
}
