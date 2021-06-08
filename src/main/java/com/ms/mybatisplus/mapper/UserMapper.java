package com.ms.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ms.mybatisplus.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface UserMapper extends BaseMapper<User> {
    List<User> selectAllByName(String name);
}
