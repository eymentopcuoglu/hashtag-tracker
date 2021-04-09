package com.topcuoglu.springbootconsumer.service;

import com.topcuoglu.springbootconsumer.dao.UserRepository;
import com.topcuoglu.springbootconsumer.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent())
            return optionalUser.get();
        else
            throw new RuntimeException("User with id:" + id + " is not found!");
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }
}
