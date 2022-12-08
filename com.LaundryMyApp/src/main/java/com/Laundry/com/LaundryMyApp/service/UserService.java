package com.Laundry.com.LaundryMyApp.service;

import java.util.List;

import com.Laundry.com.LaundryMyApp.model.UserLaundry;

public interface UserService {
    public void deleteUserId(Long id);
    public UserLaundry searchUserById(Long id);
    public UserLaundry searchUserByLogin(String login);
    public UserLaundry saveUser(UserLaundry userLaundry);
    public void altUser(UserLaundry userLaundry);
    public List<UserLaundry> userList();
    public void assignRoleToUser(long idUser, int[] idsRoles, boolean isEnable);
}