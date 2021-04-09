package com.topcuoglu.springbootconsumer.service;

import com.topcuoglu.springbootconsumer.entity.User;

import java.util.List;

public interface UserService {

    public List<User> findAll();

    public User findById(long id);

    public void save(User user);

    public void deleteById(long id);
}
