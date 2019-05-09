package com.xiaoyuanbang.user.dao;

import com.xiaoyuanbang.user.domain.User;

public interface UserDao {

    boolean hasThisUser(String openid);

    void createNewUser(String userInfo);

    String getSchool(String openid);

    User getUser(String openid);
}
