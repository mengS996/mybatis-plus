package com.ms.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author     ：孟老师
 * @date       ：Created in 2021/5/26 11:08
 * @description：
 * @modified By：
 * @version: $
 */
@Data
//@TableName(value="t_user")
public class User {
    //实现自增序列id自动插入的功能
    @TableId
    private Long id;
    //@TableField(value = "username")
    private String name;
    private Integer age;
    private String email;
    //@TableField(value = "create_time")  不需要写，多此一举
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    //master修改
    //dev修改
    //pull测试
}
