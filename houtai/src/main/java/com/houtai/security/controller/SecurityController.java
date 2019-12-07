package com.houtai.security.controller;

import com.google.gson.Gson;
import com.houtai.admin.dao.AdminDao;
import com.houtai.common.domain.Msg;
import com.houtai.security.details.TokenDetail;
import com.houtai.security.impls.TokenDetailImpl;
import com.houtai.security.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {
    private Gson g = new Gson();
    @Autowired
    AdminDao adminDao;
    @Autowired
    TokenUtil tokenUtil;

    //TODO: 希望扩展成为既可以用用户名也可以用手机号登录
    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String pwd) {
        Msg m = new Msg();
        try {
            if (adminDao.checkNameAndPwd(username, pwd) == 1) {
                m.setStatus("ok");

                TokenDetail tokenDetail = new TokenDetailImpl(username);
                m.setContent(tokenUtil.generateToken(tokenDetail));
            } else {
                m.setStatus("wrong");
                m.setContent("账号或密码不正确");
            }
        } catch (Exception e) {
            m.setStatus("error");
            m.setContent("登录失败，稍后再试");
        }
        return g.toJson(m);
    }
}
