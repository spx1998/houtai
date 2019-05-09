package com.xiaoyuanbang.user.controller;


import com.google.gson.Gson;
import com.xiaoyuanbang.common.utils.HttpRequest;
import com.xiaoyuanbang.user.dao.UserDao;
import com.xiaoyuanbang.user.domain.User;
import com.xiaoyuanbang.user.domain.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import com.xiaoyuanbang.common.domain.APPINFO;
import org.apache.commons.lang.StringUtils;

/**
 * 控制用户登录、注册、绑定学校等相关操作
 * @author spx
 */
@Controller
public class UserController {
    //需要增加日志

    private Gson g=new Gson();

    @Autowired
    UserDao userDao;

    @Transactional
    @RequestMapping("/user/login")
    public String Login(@Param("code") String code){
        String params="appid=" + APPINFO.APPID + "&secret=" + APPINFO.APPSECRET + "&js_code=" + code + "&grant_type=" + APPINFO.GRANT_TYPE;
        String userInfoJson= HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session",params);
        UserInfo userInfo =g.fromJson(userInfoJson,UserInfo.class);


        //若数据库中无该openid，则新建用户，否则，返回用户数据。

        if(!userDao.hasThisUser(userInfo.getOpenid())){

            userDao.createNewUser(userInfo.getOpenid());
        }

        /**
        *学校的绑定逻辑不应该在此方法，应该在获取用户名和头像的操作之后
         */

        User user=userDao.getUser(userInfo.getOpenid());
        //返回用户信息
        return g.toJson(user);
    }


}
