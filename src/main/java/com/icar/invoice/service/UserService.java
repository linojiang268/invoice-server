package com.icar.invoice.service;

import com.icar.invoice.po.user.User;

import java.util.List;

public interface UserService {
    User login(User user);

    User getUser(int id);

    void changePassword(User user, String password);

    List<User> listAll();
}
