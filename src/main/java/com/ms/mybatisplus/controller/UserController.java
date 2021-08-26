package com.ms.mybatisplus.controller;

import com.ms.mybatisplus.entity.User;
import com.ms.mybatisplus.service.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author     ：孟老师
 * @date       ：Created in 2021/8/23 11:02
 * @description：
 * @modified By：
 * @version: $
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/list")
    public List<User> list(){
        return userService.list();
    }
}
