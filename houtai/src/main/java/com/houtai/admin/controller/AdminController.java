package com.houtai.admin.controller;

import com.google.gson.Gson;
import com.houtai.admin.dao.AdminDao;
import com.houtai.admin.domain.AdminInfo;
import com.houtai.common.domain.Msg;
import com.houtai.security.utils.TokenUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {
    private Gson g = new Gson();

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private TokenUtil tokenUtil;

    /**
     * 超管增加管理员
     */
    @PostMapping("/admin/add")
    public String addAdmin(@RequestParam("name")String name,@RequestParam("phone")String phoneNumber){
        Msg m = new Msg();
        try {
            adminDao.addAdmin(name,phoneNumber);
            m.setStatus("ok");
            m.setContent("新建管理员成功");
        } catch (DuplicateKeyException de) {
            m.setStatus("wrong");
            m.setContent("名称或手机号重复");
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
    @PostMapping("/admin/update")
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
            }
            adminDao.changeAdminInfo(id,name,phoneNumber,password);
            m.setStatus("ok");
            m.setContent("修改成功");
        }catch (DuplicateKeyException de) {
            m.setStatus("wrong");
            m.setContent("名称或手机号已存在");
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
    @PostMapping("/admin/delete")
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
    @PostMapping("/admin/pwd")
    public String changeAdminPwd(@RequestParam("phoneNumber")String phoneNumber,@RequestParam("password")String pwd,@RequestParam("newPwd")String newpwd){
        Msg m = new Msg();
        try {
            AdminInfo adminInfo = adminDao.getAdminInfoByPhoneNumber(phoneNumber);
            if(pwd.equals(adminInfo.getPassword())) {
                adminDao.changeAdminPwd(phoneNumber,newpwd);
                m.setStatus("ok");
                m.setContent("修改成功");
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
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/list")
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

    /**
     * 获取本人信息
     */
    @GetMapping("/info")
    public String getMyInfo(@RequestHeader("token")String token){
        Msg m = new Msg();
        try {
            String userName = tokenUtil.getUsernameFromToken(token);
            AdminInfo adminInfo = adminDao.getAdminInfoByUsername(userName);
            adminInfo.setPassword("");
            m.setStatus("ok");
            m.setContent(g.toJson(adminInfo));
        }catch (Exception e){
            e.printStackTrace();
            m.setStatus("error");
        }
        return g.toJson(m);
    }
}
