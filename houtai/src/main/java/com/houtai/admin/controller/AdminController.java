package com.houtai.admin.controller;

import com.google.gson.Gson;
import com.houtai.admin.dao.AdminDao;
import com.houtai.admin.domain.AdminInfo;
import com.houtai.common.domain.Msg;
import com.houtai.common.utils.AESUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {
    private Gson g = new Gson();

    @Autowired
    private AdminDao adminDao;
    @PostMapping("/admin")


    /**
     * 超管增加管理员
     */
    public String addAdmin(@RequestParam("name")String name,@RequestParam("phone")String phoneNumber){
        Msg m = new Msg();
        try {
            adminDao.addAdmin(name,phoneNumber);
            m.setStatus("ok");
            m.setContent("新建管理员成功");
        } catch (DuplicateKeyException de) {
            m.setStatus("wrong");
            m.setContent("该手机号已存在");
        }catch (Exception e ){
            e.printStackTrace();
            m.setStatus("error");
            m.setContent("新建管理员失败");
        }
        return g.toJson(m);
    }

    /**
     *  root 修改管理员信息
     */
    @PatchMapping("/admin")
    public String changeAdminInfo(@RequestParam("id")int id,@RequestParam("name")String name,@RequestParam("phone")String phoneNumber,@RequestParam("password")String password){
        Msg m = new Msg();
        try {
            AdminInfo adminInfo = adminDao.getAdminInfoById(id);
            if(StringUtils.isBlank(name)){
                name = adminInfo.getName();
            }
            if(StringUtils.isBlank(phoneNumber)){
                phoneNumber = adminInfo.getPhoneNumber();
            }
            if(StringUtils.isBlank(password)){
                password = adminInfo.getPassword();
            }else {
                password = AESUtil.encrypt(password,AESUtil.KEY);
            }
            adminDao.changeAdminInfo(id,name,phoneNumber,password);
            m.setStatus("ok");
            m.setContent("修改成功");
        }catch (DuplicateKeyException de) {
            m.setStatus("wrong");
            m.setContent("手机号已存在");
        }
       catch (Exception e){
            e.printStackTrace();
            m.setStatus("error");
            m.setContent("修改失败");
        }
        return g.toJson(m);
    }

    /**
     *     超管删除管理员
     */
    @DeleteMapping("/admin")
    public String deleteAdmin(@RequestParam("id")int id){
        Msg m = new Msg();
        try {
            adminDao.deleteAdmin(id);
            m.setStatus("ok");
            m.setContent("删除成功");
        }catch (Exception e){
            e.printStackTrace();
            m.setStatus("error");
            m.setContent("删除失败");
        }
        return g.toJson(m);
    }

    /**
     * 管理员修改密码
     */
    @PatchMapping("/admin/pwd")
    public String changeAdminPwd(@RequestParam("id")int id,@RequestParam("password")String pwd,@RequestParam("newPwd")String newpwd){
        Msg m = new Msg();
        try {
            AdminInfo adminInfo = adminDao.getAdminInfoById(id);

            if(pwd.equals(AESUtil.decrypt(adminInfo.getPassword(), AESUtil.KEY))) {
                adminDao.changeAdminPwd(id,AESUtil.encrypt(newpwd,AESUtil.KEY));
                m.setStatus("ok");
                m.setStatus("修改成功");
            }else {
                m.setStatus("wrong");
                m.setContent("原密码错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            m.setStatus("error");
            m.setContent("修改失败");
        }
        return g.toJson(m);
    }

    /**
     *     获取管理员列表
     */
    @GetMapping("/admin")
    public String getAdminList(){
        Msg m = new Msg();
        try {
            List<AdminInfo> adminInfos = adminDao.getAdminList();
            m.setStatus("ok");
            m.setContent(g.toJson(adminInfos));
        }catch (Exception e){
            e.printStackTrace();
            m.setStatus("error");
            m.setContent("列表获取失败");
        }
        return g.toJson(m);
    }

}
