package com.ms.mybatisplus.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author     ：孟老师
 * @date       ：Created in 2021/6/18 11:17
 * @description：
 * @modified By：
 * @version: $
 */
@Slf4j
@Component //添加进Spring容器
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        //添加操作时，createTime自动生成当前时间
        this.strictInsertFill(metaObject,"createTime", LocalDateTime.class,LocalDateTime.now());
        //添加操作时，updateTime也自动生成当前时间
        this.strictInsertFill(metaObject,"updateTime", LocalDateTime.class,LocalDateTime.now());
        //判断是否具有author属性
        boolean hasAuthor = metaObject.hasSetter("author");
        if(hasAuthor){
            log.info("start insert fill author....");
            this.strictInsertFill(metaObject, "author", String.class, "燕南天");
        }
        //判断age是否赋值
        Object age = this.getFieldValByName("age", metaObject);
        if (age==null){
            log.info("start insert fill age....");
            this.strictInsertFill(metaObject, "age", String.class, "18");
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());

    }
}
