/**
 * 
 */
package com.niu.myapp.myapp.base.data.localdata.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来标注一个类对应数据库中的某张表
 * 
 * @author huangshan1 2014-12-17
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DBTable {
    /**
     * 代表数据库的表的名称
     */
    String name();
}
