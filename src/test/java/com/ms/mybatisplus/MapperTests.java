package com.ms.mybatisplus;

import com.ms.mybatisplus.entity.Product;
import com.ms.mybatisplus.entity.User;
import com.ms.mybatisplus.mapper.ProductMapper;
import com.ms.mybatisplus.mapper.UserMapper;
import com.ms.mybatisplus.mapper.UserMapper;
import com.sun.xml.internal.fastinfoset.stax.factory.StAXOutputFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author     ：孟老师
 * @date       ：Created in 2021/5/26 16:22
 * @description：
 * @modified By：
 * @version: $
 */
@SpringBootTest
public class MapperTests {
    @Resource
    private UserMapper userMapper;

    @Resource
    private ProductMapper productMapper;

    @Test
    public void testConcurrentUpdate() {
        //1、小李取数据 100
        Product p1 = productMapper.selectById(1L);
        //2、小王取数据 100
        Product p2 = productMapper.selectById(1L);

        //3、小李将价格加了50元，存入了数据库
        p1.setPrice(p1.getPrice() + 50);
        int result1 = productMapper.updateById(p1);
        System.out.println("小李修改结果：" + result1);

        //4、小王将商品减了30元，存入了数据库
        p2.setPrice(p2.getPrice() - 30);
        int result2 = productMapper.updateById(p2);
        System.out.println("小王修改结果：" + result2);
        if(result2==0){
            //小王重新获取数据,此时price是150
            p2=productMapper.selectById(1L);
            p2.setPrice(p2.getPrice() - 30);//150-30
            result2=productMapper.updateById(p2);  //更新到数据库
        }
        System.out.println("小王重新修改后结果：" + result2);

        //最后的结果
        Product p3 = productMapper.selectById(1L);
        System.out.println("最后的结果：" + p3.getPrice());
    }

    @Test
    public void testInsert(){
        User user = new User();
        user.setName("Rose1");
        user.setAge(18);
        user.setEmail("rose@163.com");
        //注释掉，使用数据库添加CURRENT_TIMESTAMP
        //user.setCreateTime(LocalDateTime.now());
        //user.setUpdateTime(LocalDateTime.now());
        int insert = userMapper.insert(user);
        System.out.println("插入了"+insert+"条数据。");
    }
    @Test
    public void testSelect(){
        //按id查询,此处的1是包装类行，自动装箱
        User user = userMapper.selectById(1);
        System.out.println(user);
        //按id列表查询  批量查询
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
        users.forEach(System.out::println);
        //按条件查询
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Tom"); //注意此处是表中的列名，不是类中的属性名
        map.put("age", 28);
        List<User> users1 = userMapper.selectByMap(map);
        users1.forEach(System.out::println);
    }
    @Test
    public void testUpdate(){
        User user = new User();
        user.setId(1L);
        user.setAge(90);
        //注意：update时生成的sql自动是动态sql,根据id修改age字段的值，name与email值没变。
        int result = userMapper.updateById(user);
        System.out.println("影响的行数：" + result);
    }
    @Test
    public void testDelete(){
        int result = userMapper.deleteById(1397788416878899204L);
        System.out.println("影响的行数：" + result);
    }
    @Test
    public void testSelectAllByName(){
        List<User> users = userMapper.selectAllByName("Tom");
        users.forEach(System.out::println);
    }
}
