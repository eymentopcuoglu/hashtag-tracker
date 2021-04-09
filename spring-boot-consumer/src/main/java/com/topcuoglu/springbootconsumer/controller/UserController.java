package com.topcuoglu.springbootconsumer.controller;

import com.topcuoglu.springbootconsumer.entity.User;
import com.topcuoglu.springbootconsumer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable long id) {
        return userService.findById(id);
    }

    @PostMapping
    public User saveUser(@RequestBody User user) {
        user.setId(0);
        userService.save(user);
        return user;
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user, @PathVariable long id) {
        user.setId(id);
        userService.save(user);
        return user;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteById(id);
    }
}
