package com.naseko.enkiapi.biz.service;

import com.naseko.enkiapi.biz.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Long id);

    User findByUsername(String Username);

    User login(String Username, String Password);

    void register(User user);

    void update(User user);

    public void delete(Long id);
}
