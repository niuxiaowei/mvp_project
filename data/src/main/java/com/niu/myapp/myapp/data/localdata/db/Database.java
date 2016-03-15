/**
 * 
 */
package com.niu.myapp.myapp.data.localdata.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.niu.myapp.myapp.common.util.DLog;
import com.niu.myapp.myapp.common.util.SerializedUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 数据库抽象类，该类的子类代表一个 SQLite 数据库。该类可根据添加了标注的 model 类自动建表、更新表，
 * 并简化常规的插入、查询、更新等操作而无需关心具体的 Java 对象和数据库对象间的映射关系。
 * 
 * <p>
 * 假设你想创建一个城市相关的数据库，这个数据库中包含两张表：省份表和城市表，那么你可以创建两个继承自 {@link ORMModel} 的 model 类，
 * 用来映射到城市数据库中的两张表，另外，再创建一个派生自 {@link Database} 的子类，用来表示你的数据库。<br>
 * 例如你可以这样编写你的省份类：
 * <p>
 *
 * <pre>
 *     {@literal @}DBTable(name = Province.TABLE_NAME)
 *     public static class Province extends ORMModel {
 *         public static final String TABLE_NAME = "province";
 *         public static final String COL_PROVINCE_ID = "provinceId";
 *         public static final String COL_PROVINCE_NAME = "provinceName";
 *
 *         {@literal @}DBColumn(name = COL_PROVINCE_ID)
 *         public int provinceId; // 省份id
 *
 *         {@literal @}DBColumn(name = COL_PROVINCE_NAME)
 *         public String provinceName; // 省份名称
 *
 *         public String anythingElse; // 其他不需要保存到数据库的字段
 *     }
 * </pre>
 * <p>
 * 上面的省份类添加了 {@link DBTable} 标注，表示该类对应于一张数据库表，并且 DBTable 标注的 name 属性被设置成
 * province（Province.TABLE_NAME 常量的值）， 表示该类对应的数据库表的名称为
 * province。该类有两个公开的字段，这两个字段都添加了 {@link DBColumn} 标注， 表示这两个字段分别对应数据库表 province
 * 的两个数据列，并且名称分别为 provinceId 和 provinceName。另外，provinceId 和 provinceName 字段的类型
 * int/String 也隐式的表明，这两个字段对应的数据列的类型分别是 NUMBER 和 TEXT。
 * </p>
 *
 * <p>
 * 下面是一个更加复杂的城市类，它同样表示数据库中的一张表：
 * </p>
 *
 * <pre>
 * <code>
 *     {@literal @}DBTable(name = City.TABLE_NAME)
 *     public static class City extends ORMModel {
 *         public static final String TABLE_NAME = "city";
 *         public static final String COL_CITY_ID = "cityId";
 *         public static final String COL_PROVINCE_ID = "provinceId";
 *         public static final String COL_CITY_NAME = "cityName";
 *         public static final String COL_CITY_SCRIPT_INDEX = "cityScriptIndex";
 *         public static final String COL_IS_HOT = "isHot";
 *         public static final String COL_LATITUDE = "latitude";
 *         public static final String COL_TEMPERATURE = "temperature";
 *         public static final String COL_DISTRICTS = "districts";
 *         public static final String COL_CREATE_TIME = "createTime";
 *
 *         {@literal @}DBColumn(name = COL_CITY_ID)
 *         public int cityId; // 城市id
 *
 *         {@literal @}DBColumn(name = COL_PROVINCE_ID)
 *         public int provinceId; // 对应的省份id
 *
 *         {@literal @}DBColumn(name = COL_CITY_NAME)
 *         public String cityName; // 城市名称
 *
 *         {@literal @}DBColumn(name = COL_CITY_SCRIPT_INDEX)
 *         public short cityScriptIndex; // 城市 scriptIndex
 *
 *         {@literal @}DBColumn(name = COL_IS_HOT)
 *         public boolean isHot; // 是否是热门城市
 *
 *         {@literal @}DBColumn(name = COL_LATITUDE)
 *         public double latitude; // 城市的纬度
 *
 *         {@literal @}DBColumn(name = COL_TEMPERATURE)
 *         public float temperature; // 城市当前的温度
 *
 *         {@literal @DBColumn(name = COL_DISTRICTS)
 *         public ArrayList<District> districts;} // 城市的区域列表，这个字段类型是复杂类型，但由于是可序列化的，所以也可以添加 DBColumn 标注（对应的数据库列的类型为 BLOB）
 *
 *         {@literal @}DBColumn(name = COL_CREATE_TIME)
 *         public long createTime; // 创建的时间
 *     }
 *
 *     {@literal // 可序列化的区域类}
 *     public static class District implements Serializable {
 *         public int districtId;
 *         public String districtName;
 *     }
 * </code>
 * </pre>
 *
 * <p>
 * 接下来，你可以创建一个派生自 {@link Database} 的子类，用来表示你的数据库：
 * </p>
 *
 * <pre>
 * <code>
 *     public static class CityDatabase extends Database {
 *
 *         {@literal @}Override
 *         protected String getDBName() {
 *             // 返回数据库文件名称
 *             return "CityTest.db";
 *         }
 *
 *         {@literal @}Override
 *         protected int getDBVersion() {
 *             // 返回当前的数据库版本，如果下一版本 Model 有变化，如新增或删减字段，版本号可直接 +1
 *             return 4;
 *         }
 *
 *         {@literal @}Override
 *         protected List<Class<? extends ORMModel>> getDBTables() {
 *             // 该数据库中包含两张表，表的信息由 Province 和 City 类的 Annotation 给出
 *             {@literal ArrayList<Class<? extends ORMModel>> tables = new ArrayList<Class<? extends ORMModel>>();}
 *             tables.add(Province.class);
 *             tables.add(City.class);
 *             return tables;
 *         }
 *
 *     }
 *     </code>
 * </pre>
 *
 * <p>
 * 最后，你可以使用 CityDatabase 来执行常规的数据库操作，例如：
 * </p>
 *
 * <pre>
 * <code>
 *         // 创建数据库对象（内部会自动创建数据库或更新数据库）
 *         CityDatabase cityDB = CityDatabase.getInstance();
 *
 *         // 插入省份数据
 *         Province province = new Province();
 *         province.provinceId = 0;
 *         province.provinceName = "海南省";
 *         int affectCount = cityDB.insert(province);
 *
 *         // 查询省份
 *         {@literal List<Province> provinces = cityDB.query("SELECT * FROM " + Province.TABLE_NAME, Province.class)};
 * </code>
 * </pre>
 *
 * @author huangshan1 2014-12-17
 *
 */
public abstract class Database {
    private static final String TAG = "ORM-Database";

    private static HashMap<Class<? extends ORMModel>, ClassReflection> classReflections = new HashMap<Class<? extends ORMModel>, ClassReflection>();

    private OpenHelper mOpenHelper;

    public Database(Context context) {
        mOpenHelper = new OpenHelper(context);
    }

    /**
     * 子类应实现该方法返回数据库的名称
     */
    protected abstract String getDBName();

    /**
     * 子类应实现该方法返回数据库的版本号
     */
    protected abstract int getDBVersion();

    /**
     * 子类应实现该方法返回数据库中包含的所有的表对应的 Java Model 类
     */
    protected abstract List<Class<? extends ORMModel>> getDBTables();




    /**
     * 创建数据库，在需要创建数据库时自动调用。该方法内部会自动建表，子类最好不要覆盖该方法。
     */
    protected void onDBCreate(SQLiteDatabase db) {
        DLog.i(TAG, "creating database...");
        for (Class<? extends ORMModel> tableClass : getDBTables()) {
            // 建表
            StringBuilder sb = new StringBuilder();
            ClassReflection cRfl = getClassReflection(tableClass);
            sb.append("CREATE TABLE ").append(cRfl.tableName).append(" (");
            sb.append("_id INTEGER PRIMARY KEY AUTOINCREMENT,");
            for (int i = 0, size = cRfl.fieldReflections.size(); i < size; i++) {
                FieldReflection fRfl = cRfl.fieldReflections.get(i);
                if (fRfl.columnName.equals("_id")) {
                    continue;
                }

                Class<?> fieldType = fRfl.field.getType();
                if (fieldType.equals(Double.TYPE) || fieldType.equals(Long.TYPE) || fieldType.equals(Integer.TYPE) || fieldType.equals(Short.TYPE)
                        || fieldType.equals(Byte.TYPE) || fieldType.equals(Boolean.TYPE)) {
                    // 数字类型
                    sb.append(fRfl.columnName).append(" INTEGER");
                } else if (fieldType.equals(Float.TYPE)) {
                    // 浮点数类型
                    sb.append(fRfl.columnName).append(" REAL");
                } else if (fieldType.equals(String.class)) {
                    // 字符串类型
                    sb.append(fRfl.columnName).append(" TEXT");
                } else if (isSerializableField(fieldType)) {
                    // 可序列化类型
                    sb.append(fRfl.columnName).append(" BLOB");
                } else {
                    // 不支持的类型
                    throw new RuntimeException("can't map field to database column: " + fRfl.field);
                }
                sb.append(",");
            }
            if (sb.charAt(sb.length() - 1) == ',') {
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append(");");
            db.execSQL(sb.toString());

            if (DLog.loggable) {
                DLog.i(TAG, "create sqlite table, sql:\n" + sb.toString());
            }
        }
    }

    /**
     * 升级数据库，在需要升级数据库时自动调用。默认的更新方式为：先删除所有表，然后再重新创建他们。
     * 子类在需要时可视情况实现自己的升级逻辑，比如是否需要创建临时表以保存老数据，在建完新表后再写回去。
     */
    protected void onDBUpgrade(SQLiteDatabase db) {
        DLog.i(TAG, "upgrading database...");
        for (Class<? extends ORMModel> tableClass : getDBTables()) {
            ClassReflection cRef = getClassReflection(tableClass);
            db.execSQL("DROP TABLE IF EXISTS " + cRef.tableName);
        }

        onDBCreate(db);
    }

    /**
     * 执行查询语句，并返回其对应的数据列表。
     *
     * @param <T> 继承自 {@link ORMModel} 的类型
     * @param sql 查询语句
     * @param tableClass 要返回的 Model 类的类型
     * @return 查询到的并且已经完成数据库对象->Java 对象转换的数据列表
     */
    public <T extends ORMModel> List<T> query(String sql, Class<T> tableClass) {
        DLog.i(TAG, "query sql: " + sql);
        List<T> list = new ArrayList<T>();

        ClassReflection cr = getClassReflection(tableClass);
        if (cr != null) {
            Cursor cursor = null;
            try {
                cursor = mOpenHelper.getReadableDatabase().rawQuery(sql, null);
                if (cursor.moveToFirst()) {
                    do {
                            T t = tableClass.newInstance();
                            for (FieldReflection fr : cr.fieldReflections) {
                                Class<?> fieldType = fr.field.getType();
                                fr.field.setAccessible(true);

                                int columnIdx = cursor.getColumnIndex(fr.columnName);
                                if (columnIdx != -1) {
                                    if (fieldType.equals(Double.TYPE)) {
                                        fr.field.setDouble(t, cursor.getDouble(columnIdx));
                                    } else if (fieldType.equals(Long.TYPE)) {
                                        fr.field.setLong(t, cursor.getLong(columnIdx));
                                    } else if (fieldType.equals(Integer.TYPE)) {
                                        fr.field.setInt(t, cursor.getInt(columnIdx));
                                    } else if (fieldType.equals(Float.TYPE)) {
                                        fr.field.setFloat(t, cursor.getFloat(columnIdx));
                                    } else if (fieldType.equals(Short.TYPE)) {
                                        fr.field.setShort(t, cursor.getShort(columnIdx));
                                    } else if (fieldType.equals(Byte.TYPE)) {
                                        fr.field.setByte(t, (byte) cursor.getShort(columnIdx));
                                    } else if (fieldType.equals(Boolean.TYPE)) {
                                        fr.field.setBoolean(t, cursor.getInt(columnIdx) == 1);
                                    } else if (fieldType.equals(String.class)) {
                                        fr.field.set(t, cursor.getString(columnIdx));
                                    } else if (isSerializableField(fieldType)) {
                                        byte[] blob = cursor.getBlob(columnIdx);
                                        fr.field.set(t, SerializedUtils.deserializeObject(blob));
                                    }
                                }
                            }
                            list.add(t);
                    } while (cursor.moveToNext());
                }
            } catch (Exception e) {
                DLog.e(TAG, e);
                // 发生任何异常直接返回空列表
                return list;
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }

        return list;
    }



    /**
     * 批量插入数据列表
     *
     * @param <T> 继承自 {@link ORMModel} 的类型
     * @param list 要插入的 Java 对象列表，列表中的每一个对象必须继承自 {@link ORMModel}
     * @return 成功插入的条数
     */
    public <T extends ORMModel> int insert(List<T> list) {
        if(list == null){
            return 0;
        }
        DLog.i(TAG, "insert list: " + list);
        int insertCount = 0;
        SQLiteDatabase db = null;
        try {
            db = mOpenHelper.getWritableDatabase();
            db.beginTransaction();
            for (T t : list) {
                ContentValues cv = getContentValuesFromModel(t);
                if (cv != null) {
                    Class<? extends ORMModel> tableClass = (Class<? extends ORMModel>) t.getClass();
                    ClassReflection cRfl = getClassReflection(tableClass);
                    db.insert(cRfl.tableName, null, cv);
                    insertCount++;
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            DLog.e(TAG, e);
            insertCount = 0;
        } finally {
            try {
                if (db != null) {
                    db.endTransaction();
                }
            } catch (Exception e2) {
                DLog.e(TAG, e2);
            }
        }
        DLog.i(TAG, "insert insertCount: " + insertCount);
        return insertCount;
    }

    /**
     * 插入单条记录，参考 {@link #insert(List)} 方法
     */
    public int insert(ORMModel model) {
        ArrayList<ORMModel> list = new ArrayList<ORMModel>();
        list.add(model);
        return insert(list);
    }

    /**
     * 批量更新数据库记录
     *
     * @param <T> 继承自 {@link ORMModel} 的类型
     * @param list 要更新的 Java 对象列表，列表中的每一个对象必须继承自 {@link ORMModel}
     * @return 受影响的条数
     */
    public <T extends ORMModel> int update(List<T> list) {
        int affects = 0;
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        try {
            db.beginTransaction();
            for (T t : list) {
                ContentValues cv = getContentValuesFromModel(t);
                if (cv != null) {
                    Class<? extends ORMModel> tableClass = (Class<? extends ORMModel>) t.getClass();
                    ClassReflection cRfl = getClassReflection(tableClass);
                    affects += db.update(cRfl.tableName, cv, "_id=" + t._id, null);
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            DLog.e(TAG, e);
            affects = 0;
        } finally {
            try {
                db.endTransaction();
            } catch (Exception e2) {
            }
        }
        return affects;
    }

    /**
     * 更新单条记录，参考 {@link #update(List)} 方法
     */
    public int update(ORMModel model) {
        ArrayList<ORMModel> list = new ArrayList<ORMModel>();
        list.add(model);
        return update(list);
    }
    
    /**
     * 删除某条记录
     * 
     * @param model 要删除的记录
     * @return sql 语句执行是否成功
     */
    public boolean delete(ORMModel model) {
        ClassReflection cRfl = getClassReflection(model.getClass());
        if (cRfl != null) {
            return execSql("DELETE FROM " + cRfl.tableName + " WHERE _id=" + model._id);
        }
        return false;
    }
    
    /**
     * 执行任意标准 sql 语句，比如清除某张表里的数据
     * 
     * @param sql 要执行的语句
     * @return sql 语句执行是否成功
     */
    public boolean execSql(String sql) {
        try {
            DLog.i(TAG, "execute sql: " + sql);
            mOpenHelper.getWritableDatabase().execSQL(sql);
            return true;
        } catch (Exception e) {
            DLog.e(TAG, e);
        }
        return false;
    }
    
    /**
     * 获取 sql 语句查询到的记录条数
     * @param sql 查询语句，必须包含 COUNT() 方法，且 column 数为 1，例如：SELECT COUNT(*) FROM city WHERE cityId=12
     * @return
     */
    public int queryCount(String sql) {
        int count = 0;
        Cursor cursor = null;
        try {
            cursor = mOpenHelper.getReadableDatabase().rawQuery(sql, null);
            if (cursor.moveToFirst() && cursor.getColumnCount() == 1) {
                count = cursor.getInt(0);
            }
        } catch (Exception e) {
            DLog.e(TAG, e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return count;
    }
    
    /**
     * 返回当前的 SQLiteOpenHelper 对象，可使用该对象执行更加复杂的数据库操作
     */
    public SQLiteOpenHelper getSQLiteOpenHelper() {
        return mOpenHelper;
    }
    
    private ContentValues getContentValuesFromModel(ORMModel model) {
        Class<? extends ORMModel> tableClass = (Class<? extends ORMModel>) model.getClass();
        ClassReflection cr = getClassReflection(tableClass);
        if (cr != null) {
            try {
                ContentValues cv = new ContentValues();
                for (FieldReflection fr : cr.fieldReflections) {
                    if (fr.columnName.equals("_id")) {
                        continue;
                    }
                    
                    Class<?> fieldType = fr.field.getType();
                    if (fieldType.equals(Double.TYPE)) {
                        cv.put(fr.columnName, fr.field.getDouble(model));
                    } else if (fieldType.equals(Long.TYPE)) {
                        cv.put(fr.columnName, fr.field.getLong(model));
                    } else if (fieldType.equals(Integer.TYPE)) {
                        cv.put(fr.columnName, fr.field.getInt(model));
                    } else if (fieldType.equals(Float.TYPE)) {
                        cv.put(fr.columnName, fr.field.getFloat(model));
                    } else if (fieldType.equals(Short.TYPE)) {
                        cv.put(fr.columnName, fr.field.getShort(model));
                    } else if (fieldType.equals(Byte.TYPE)) {
                        cv.put(fr.columnName, fr.field.getByte(model));
                    } else if (fieldType.equals(Boolean.TYPE)) {
                        cv.put(fr.columnName, fr.field.getBoolean(model));
                    } else if (fieldType.equals(String.class)) {
                        cv.put(fr.columnName, (String) fr.field.get(model));
                    } else if (isSerializableField(fieldType)) {
                        cv.put(fr.columnName, SerializedUtils.getSerializedBytes(fr.field.get(model)));
                    }
                }
                
                return cv;
            } catch (Exception e) {
                DLog.e(TAG, e);
            }
        }
        return null;
    }


    
    private boolean isSerializableField(Class<?> fieldType) {
        // 除了 IntentExtra 类型之外的其他可序列化类型，才允许持久化
        return Serializable.class.isAssignableFrom(fieldType);
    }
    
    /**
     * 找出 klass 中所有关联数据库字段的成员变量，并加入缓存
     */
    private ClassReflection getClassReflection(Class<? extends ORMModel> klass) {
        ClassReflection cf = null;
        if (klass.isAnnotationPresent(DBTable.class)) {
            cf = classReflections.get(klass);
            if (cf == null) {
                cf = new ClassReflection();
                cf.tableName = klass.getAnnotation(DBTable.class).name();
                cf.fieldReflections = new ArrayList<FieldReflection>();
                for (Field field : klass.getFields()) {
                    if (field.isAnnotationPresent(DBColumn.class)) {
                        FieldReflection f = new FieldReflection();
                        f.field = field;
                        f.columnName = field.getAnnotation(DBColumn.class).name();
                        cf.fieldReflections.add(f);
                    }
                }
                
                classReflections.put(klass, cf);
            }
        }
        return cf;
    }
    
    private static class ClassReflection {
        String tableName;
        ArrayList<FieldReflection> fieldReflections;
    }
    
    private static class FieldReflection {
        Field field;
        String columnName;
    }
    
    private class OpenHelper extends SQLiteOpenHelper {
        
        public OpenHelper(Context context) {
            super(context, getDBName(), null, getDBVersion());
        }
        
        @Override
        public void onCreate(SQLiteDatabase db) {
            onDBCreate(db);
        }
        
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onDBUpgrade(db);
        }
        
    }
}
