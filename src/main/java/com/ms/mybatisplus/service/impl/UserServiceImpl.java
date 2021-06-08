package com.ms.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ms.mybatisplus.entity.User;
import com.ms.mybatisplus.mapper.UserMapper;
import com.ms.mybatisplus.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author     ：孟老师
 * @date       ：Created in 2021/5/27 10:56
 * @description：
 * @modified By：
 * @version: $
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    //第一种方案，第二种方案使用baseMapper
    @Resource
    private UserMapper userMapper;
    @Override
    public List<User> listAllByName(String name) {
        //return userMapper.selectAllByName(name);
        return baseMapper.selectAllByName(name);
    }
}
