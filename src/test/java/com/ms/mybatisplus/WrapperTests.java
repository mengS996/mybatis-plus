package com.ms.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ms.mybatisplus.entity.User;
import com.ms.mybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author     ：孟老师
 * @date       ：Created in 2021/6/24 11:22
 * @description：
 * @modified By：
 * @version: $
 */

@SpringBootTest
public class WrapperTests {
    @Resource
    private UserMapper userMapper;

    /**
     * 模糊查询：查询名字中包含e，年龄大于等于10且小于等于20，email不为空的用户
     *  大于：gt  小于：lt
     */
    @Test
    public void test1(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //链式编程，name为数据库列名!
        queryWrapper
                .like("name","e")
                .between("age",10,20)
                .isNotNull("email");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }
    /**
     * 组装排序条件：按年龄降序查询用户，如果年龄相同则按id升序排列
     */
    @Test
    public void test2(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("age").orderByAsc("id");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }
    /**
     * 组装删除条件:删除email为空的用户
     */
    @Test
    public void test3() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("email");
        //条件构造器也可以构建删除语句的条件
        int result = userMapper.delete(queryWrapper);
        System.out.println("delete return count = " + result);
    }
    /**
     * 条件的优先级:
     * 查询名字中包含n，且（年龄小于18或email为空的用户），并将这些用户的年龄设置为18，邮箱设置为 user@ms.com
     */
    @Test
    public void test4() {
        //组装查询条件，修改条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .like("name","n")
                .and(i -> i.lt("age",18).or().isNull("email"));
        //组装修改条件
        User user = new User();
        user.setAge(18);
        user.setEmail("user@ms.com");
        int result = userMapper.update(user, queryWrapper);
        System.out.println(result);
    }

}
