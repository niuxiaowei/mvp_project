package com.niu.myapp.myapp.common.util;

import android.os.Environment;

import java.io.File;

/**
 * Created by niuxiaowei on 2015/10/19.
 * 文件相關的工具類
 */
public class FileUtils {

    //緩存圖片的路徑
    public static  String CACHE_IMAGES_FILE_PATH = new StringBuilder()
            .append(Environment.getExternalStorageDirectory()
                    .getAbsolutePath()).append(File.separator)
            .append("myapp").append(File.separator)
            .append("ImageCache").append(File.separator)
            .append("images").toString();
}
