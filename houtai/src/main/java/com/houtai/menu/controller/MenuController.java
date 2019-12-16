package com.houtai.menu.controller;

import com.google.gson.Gson;
import com.houtai.common.domain.Msg;
import com.houtai.common.utils.FileUtil;
import com.houtai.menu.dao.DishDao;
import com.houtai.menu.dao.DishStatsDao;
import com.houtai.menu.domain.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@RestController
public class MenuController {
    @Autowired
    DishDao dishDao;
    @Autowired
    DishStatsDao dishStatsDao;

    private Gson g = new Gson();

    @Value("${web.upload-path}")
    String path;

    @Value("${Nginx-picture-path}")
    String nginxPath;

    /**
     * 获取dish列表
     */
    @GetMapping("/dish/list")
    public String getDishList() {
        Msg m = new Msg();
        try {
            List<Dish> dishes = dishDao.getDishList();
            m.setStatus("ok");
            m.setContent(g.toJson(dishes));
        } catch (Exception e) {
            e.printStackTrace();
            m.setStatus("error");
            m.setContent("获取列表失败");
        }
        return g.toJson(m);
    }

    /**
     * 获取dish详情
     */
    @GetMapping("/dish/{id}")
    public String getDish(@PathVariable("id") int id) {
        Msg m = new Msg();
        try {
            Dish dish = dishDao.getDishById(id);
            m.setStatus("ok");
            m.setContent(g.toJson(dish));
        } catch (Exception e) {
            e.printStackTrace();
            m.setStatus("error");
            m.setContent("获取菜品详情失败");
        }
        return g.toJson(m);
    }

    /**
     * 添加菜品
     */
    @PostMapping("/dish/add")
    public String addDish(@RequestBody String jsonString) {
        Msg m = new Msg();
        try {
            Dish dish = g.fromJson(jsonString, Dish.class);
            dishDao.addDish(dish.getName(), dish.getIntroduce(), dish.getPicUrl(), dish.getPrice(), dish.getvPrice(), dish.getType());
            //TODO: 需要加一个检测的定时任务
            dishStatsDao.addDish(dish.getDishID());
            m.setStatus("ok");
        } catch (Exception e) {
            e.printStackTrace();
            m.setStatus("error");
        }
        return g.toJson(m);
    }

    /**
     * 上传图片
     */
    @PostMapping("/dish/picture")
    public String addDishPicture(@RequestParam("picture") MultipartFile pic) {
        String pictureName;
        Msg m = new Msg();
        try {
            //生成文件名,originalFileName为文件名
            String extendedName = Objects.requireNonNull(pic.getOriginalFilename()).substring(pic.getOriginalFilename().lastIndexOf("."));
            pictureName = System.currentTimeMillis() + extendedName;
            //存储
            boolean uploadSuccessful = FileUtil.upload(pic, path, pictureName);
            //返回信息，返回路径
            if (!uploadSuccessful) {
                m.setStatus("wrong");
                m.setContent("上传失败");
                return g.toJson(m);
            }
            m.setStatus("ok");
            m.setContent(nginxPath + "/" + pictureName);
        } catch (Exception e) {
            e.printStackTrace();
            m.setStatus("error");
            m.setContent("上传失败");
        }
        return g.toJson(m);
    }

    /**
     * 删除菜品
     */
    @PostMapping("/dish/del")
    public String deleteDish(@RequestParam("id") String id) {
        Msg m = new Msg();
        try {
            dishDao.deleteDish(id);
            dishStatsDao.deleteDish(id);
            m.setStatus("ok");
        } catch (Exception e) {
            e.printStackTrace();
            m.setStatus("error");
        }
        return g.toJson(m);
    }

    /**
     * 修改菜品信息
     */
    @PostMapping("/dish/update")
    public String updateDish(@RequestBody Dish dish) {
        Msg m = new Msg();
        try {
            dishDao.updateDish(dish);
            m.setStatus("ok");
        } catch (Exception e) {
            e.printStackTrace();
            m.setStatus("error");
        }
        return g.toJson(m);
    }


}
