package com.icar.invoice.service.impl;

import com.icar.invoice.mapper.UserMapper;
import com.icar.invoice.po.user.User;
import com.icar.invoice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(User user) {
        User rstUser = this.userMapper.getUserByUsername(user.getUsername());

        if (null != rstUser && rstUser.getPassword().equals(user.getPassword())) {
            return rstUser;
        }
        return null;
    }

    @Override
    public User getUser(int id) {
        return this.userMapper.getUserById(id);
    }

    @Override
    public void changePassword(User user, String password) {
        user.setPassword(password);
        this.userMapper.changePassword(user);
    }

    @Override
    public List<User> listAll() {
        return this.userMapper.findAll();
    }
}
