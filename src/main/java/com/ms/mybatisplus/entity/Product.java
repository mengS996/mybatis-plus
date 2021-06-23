package com.ms.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
/**
 * @author     ：孟老师
 * @date       ：Created in 2021/6/23 11:04
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class Product {
    private Long id;
    private String name;
    private Integer price;
    @Version
    private Integer version;
}
