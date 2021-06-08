package com.ms.mybatisplus;

import com.ms.mybatisplus.entity.User;
import com.ms.mybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.sql.SQLOutput;
import java.util.List;

@SpringBootTest //自动创建Spring上下文环境
class MybatisPlusApplicationTests {

    //@Autowired  //默认按类型装配，是spring的注解
    @Resource     //默认按名称装配，找不到与名称匹配的bean，则按照类型装配。是J2EE的注解
    private UserMapper userMapper;

    @Test
    void testSelectList() {
        //条件设置为null，查询全部
        List<User> users = userMapper.selectList(null);
        for(User user: users){
            System.out.println(user);
        }
    }
}
