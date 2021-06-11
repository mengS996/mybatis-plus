package com.ms.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
    private String name;
    private Integer age;
    private String email;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    //master修改
    //dev修改
}
