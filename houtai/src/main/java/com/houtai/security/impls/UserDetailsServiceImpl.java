package com.houtai.security.impls;

import com.houtai.admin.dao.AdminDao;
import com.houtai.admin.domain.AdminInfo;
import com.houtai.security.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AdminDao adminDao;

    /**
     * 获取 userDetail
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminInfo admin = adminDao.getAdminInfoByUsername(username);
        if (admin == null) {
            throw new UsernameNotFoundException(String.format("用户名不存在：", username));
        } else {

            User user = new User();
            user.setUsername(username);
            user.setPassword(admin.getPassword());
            user.setRole(admin.getRole());
            return user;
        }
    }



}
