/**
 * 
 */
package com.niu.myapp.myapp.base.data.localdata.db;


/**
 * Model 类基类。该类的子类通过添加 {@link com.niu.myapp.myapp.base.localdata.db.DBTable} 和 {@link com.niu.myapp.myapp.basedata.localdata.db.DBColumn} 标注，可代表数据库中的某张表。
 * 
 * @author huangshan1 2014-12-17
 *
 */
public class ORMModel {

    /**
     * 数据库表中的唯一记录 id
     */
    @DBColumn(name = "_id")
    public long _id;
}
