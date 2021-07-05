package com.ms.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ms.mybatisplus.entity.User;
import com.ms.mybatisplus.mapper.UserMapper;
import net.minidev.json.writer.UpdaterMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
        //i代表的就是queryWrapper
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

    /**
     * 查询所有用户的用户名和年龄
     */
    @Test
    public void test5() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name","age");
        //selectMaps()返回Map集合列表，通常配合select()使用，避免User对象中没有被查询到的列值为null
        //如果使用selectList()则会出现列为null的情况
        List<Map<String, Object>> maps = userMapper.selectMaps(queryWrapper);//返回值是Map列表
        maps.forEach(System.out::println);
    }

    /**
     * 查询id不大于3的所有用户的id列表
     */
    @Test
    public void test6() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.inSql("id", "select id from user where id <= 3");
        //selectObjs的使用场景：只返回一列
        List<User> users = userMapper.selectList(queryWrapper);//返回值是Object列表
        users.forEach(System.out::println);
    }
    /**
     * 条件的优先级: 需求同Test4.在test4的基础上修改
     * 查询名字中包含n，且（年龄小于18或email为空的用户），并将这些用户的年龄设置为18，邮箱设置为 user@ms.com
     */
    @Test
    public void test7() {
        //组装set子句
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        //i代表的就是updateWrapper
        updateWrapper
                .set("age",18)
                .set("email","user@ms.com")
                .like("name","n")
                .and(i -> i.lt("age",18).or().isNull("email"));
        //这里必须要创建User对象，否则无法应用自动填充。如果没有自动填充，可以设置为null
        User user = new User();
        int result = userMapper.update(user, updateWrapper);
        System.out.println(result);
    }

    /**
     * 查询名字中包含n，年龄大于10且小于20的用户，查询条件来源于用户输入，是可选的
     */
    @Test
    public void test8() {
        //定义查询条件，有可能为null（用户未输入）
        String name = null;
        Integer ageBegin = 10;
        Integer ageEnd = 20;
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //判断字符串不为空，当Str为空白或者null时,isNotBlank返回false
        if(StringUtils.isNotBlank(name)){
            queryWrapper.like("name","n");
        }
        if(ageBegin != null){
            queryWrapper.ge("age", ageBegin);
        }
        if(ageEnd != null){
            queryWrapper.le("age", ageEnd);
        }
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }
    @Test
    public void test8Condition() {
        //定义查询条件，有可能为null（用户未输入）
        String name = null;
        Integer ageBegin = 10;
        Integer ageEnd = 20;
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .like(StringUtils.isNotBlank(name), "name", "n")
                .ge(ageBegin != null, "age", ageBegin)
                .le(ageEnd != null, "age", ageEnd);
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }
    @Test
    public void test9() {
        //定义查询条件，有可能为null（用户未输入）
        String name = null;
        Integer ageBegin = 10;
        Integer ageEnd = 20;
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        //避免使用字符串表示字段，防止运行时错误,使用Lambda方式
        queryWrapper
                .like(StringUtils.isNotBlank(name), User::getName, "n")
                .ge(ageBegin != null, User::getAge, ageBegin)
                .le(ageEnd != null, User::getAge, ageEnd);
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }
    /**
     * 条件的优先级: 需求同Test7.在test7的基础上修改
     * 查询名字中包含n，且（年龄小于18或email为空的用户），并将这些用户的年龄设置为18，邮箱设置为 user@ms.com
     */
    @Test
    public void test10() {
        //组装set子句
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        //i代表的就是updateWrapper
        updateWrapper
                .set(User::getAge,18)
                .set(User::getEmail,"user@ms.com")
                .like(User::getEmail,"n")
                .and(i -> i.lt(User::getAge,18).or().isNull(User::getEmail));
        //这里必须要创建User对象，否则无法应用自动填充。如果没有自动填充，可以设置为null
        User user = new User();
        int result = userMapper.update(user, updateWrapper);
        System.out.println(result);
    }
}
