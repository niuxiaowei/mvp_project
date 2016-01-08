/**
 * 
 */
package com.niu.myapp.myapp.model.localdata.db;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来标注类中的一个公开字段对应数据库中某张表的一个字段。
 * 公开字段的数据类型隐式地映射到对应的数据库数据类型，当前的映射关系为：
 * <ol>
 * <li> Java 中的 double/long/int/short/byte/boolean 类型对应数据库中的 INTEGER 类型
 * <li> Java 中的 float 类型对应数据库中的 REAL 类型
 * <li> Java 中的 String 类型对应数据库中的 TEXT 类型
 * <li> Java 中的可序列化类型（实现了 {@link java.io.Serializable} 接口）对应数据库中的 BLOB 类型，在存储和读取时，会针对字段的值做序列化和反序列化。
 * <li> 其他类型时会抛出异常，因为不支持。
 * </ol>
 * 
 * @author huangshan1 2014-12-17
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DBColumn {
    /**
     * 代表数据库表中字段的名称
     */
    String name();
}
