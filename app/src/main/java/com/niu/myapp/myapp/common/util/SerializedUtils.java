package com.niu.myapp.myapp.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by niuxiaowei on 2015/10/16.
 */
public class SerializedUtils {

    /**
     * 将 Java 对象序列化成二进制数据
     *
     * @param obj 要序列化的 Java 对象
     * @return 成功序列化后的二进制数据，失败则返回 null。
     */
    public static byte[] getSerializedBytes(Object obj) {
        try {
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bao);
            out.writeObject(obj);
            out.flush();
            byte[] data = bao.toByteArray();
            out.close();
            bao.close();
            return data;
        } catch (Exception e) {
            DLog.e("Utils", e);
        }
        return null;
    }

    /**
     * 将二进制数据中反序列化成 Java 对象
     *
     * @param data 包含了序列化信息的字节数组
     * @return 成功反序列化后的 Java 对象，失败则返回 null。
     */
    public static Object deserializeObject(byte[] data) {
        if (data != null && data.length > 0) {
            try {
                ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data));
                Object obj = in.readObject();
                in.close();
                return obj;
            } catch (Exception e) {
                DLog.e("Utils", e);
            }
        }
        return null;
    }
}
