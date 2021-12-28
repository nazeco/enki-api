package com.naseko.enkiapi.biz.service.impl;

import com.naseko.enkiapi.biz.entity.User;
import com.naseko.enkiapi.biz.mapper.UserMapper;
import com.naseko.enkiapi.biz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public User findById(Long id) {
        return userMapper.findById(id);
    }

    @Override
    public User findByUsername(String Username) {
        return userMapper.findByUsername(Username);
    }

    @Override
    public User login(String Username, String Password) {
        return userMapper.findByUsernameAndPassword(Username, Password);
    }

    @Override
    public void register(User user) {
        userMapper.add(user);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public void delete(Long id) {
        userMapper.delete(id);
    }
}