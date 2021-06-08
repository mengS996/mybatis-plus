package com.ms.mybatisplus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ms.mybatisplus.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService extends IService<User> {
    List<User> listAllByName(String name);
}
