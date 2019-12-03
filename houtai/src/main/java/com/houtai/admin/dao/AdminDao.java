package com.houtai.admin.dao;

import com.houtai.admin.domain.AdminInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AdminDao {
    void addAdmin(String name, String phoneNumber);

    void changeAdminInfo(int id, String name, String phoneNumber, String password);

    void deleteAdmin(int id);

    AdminInfo getAdminInfoById(int id);

    AdminInfo getAdminInfoByPhoneNumber(String phoneNumber);

    void changeAdminPwd(String phoneNumber, String newpwd);

    List<AdminInfo> getAdminList();

    AdminInfo getAdminInfoByUsername(String username);

    int checkNameAndPwd(String name, String pwd);
}
