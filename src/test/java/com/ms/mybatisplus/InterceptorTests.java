package com.ms.mybatisplus;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ms.mybatisplus.entity.User;
import com.ms.mybatisplus.mapper.UserMapper;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author     ：孟老师
 * @date       ：Created in 2021/6/21 15:34
 * @description：
 * @modified By：
 * @version: $
 */
@SpringBootTest
public class InterceptorTests {
    @Resource
    private UserMapper userMapper;
    @Test
    public void testSelectPage(){
        //创建分页参数:当前页current：2，当前页：5条数据
        Page<User> pageParam=new Page<>(2,5);
        //执行分页查询，
        userMapper.selectPage(pageParam,null);
        //查看分页参数的成员
        List<User> users = pageParam.getRecords();
        users.forEach(System.out::println);

        //获取总记录条数
        long total = pageParam.getTotal();
        System.out.println(total);
        //查询是否存在上一页
        boolean bp = pageParam.hasPrevious();
        System.out.println("上一页："+bp);
        //查询是否存在下一页
        boolean bn = pageParam.hasNext();
        System.out.println("下一页"+bn);
    }
    @Test
    public void testSelectPageByage(){
        Page<Object> pageParam = new Page<>(1, 3);
        userMapper.selectPageByPage(pageParam,30);
        //查询的数据存放在pageParam
        List<Object> users = pageParam.getRecords();
        users.forEach(System.out::println);
    }
}
