package com.houtai.menu.controller;

import com.google.gson.Gson;
import com.houtai.common.domain.Msg;
import com.houtai.menu.dao.TypeDao;
import com.houtai.menu.domain.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TypeController {
    @Autowired
    TypeDao typeDao;

    private Gson g = new Gson();

    /**
     * 获取菜品类型列表
     */
    @GetMapping("/dish/type")
    public String getTypeList() {
        Msg m = new Msg();
        try {
            List<Type> types = typeDao.getTypeList();
            m.setStatus("ok");
            m.setContent(g.toJson(types));
        } catch (Exception e) {
            e.printStackTrace();
            m.setStatus("wrong");
        }
        return g.toJson(m);
    }

    /**
     * 新建分类
     */
    @PostMapping("/dish/type/add")
    public String addType(@RequestParam("name")String name){
        Msg m = new Msg();
        try {
            typeDao.addType(name);
            m.setStatus("ok");
        }catch (Exception e){
            e.printStackTrace();
            m.setStatus("error");
        }
        return g.toJson(m);
    }
    /**
     * 删除分类
     */
    @PostMapping("/dish/type/delete")
    public String deleteType(@RequestParam("id") int id){
        Msg m = new Msg();
        try {
            typeDao.deleteType(id);
            m.setStatus("ok");
        }catch (Exception e){
            e.printStackTrace();
            m.setStatus("error");
        }
        return g.toJson(m);
    }
}
