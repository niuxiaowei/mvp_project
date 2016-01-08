package com.niu.myapp.myapp.common.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.niu.myapp.myapp.BuildConfig;
import com.niu.myapp.myapp.Envi;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 简单的日志输出控制类，可通过 {@link #loggable} 控制是否输出日志。
 * 
 * @author niuxiaowei
 *
 */
public class DLog {

    /**
     * 是否允许输出日志
     */
	public static boolean loggable = true;

    private final static String FILE_DIR_NAME = "Log";
    private final static String FILE_NAME = "GanjiLog.txt";
	
	public static void v(String tag, String msg) {
	    if (loggable) {
	        Log.v(tag, msg);
	    }
	}
    
    public static void d(String tag, String msg) {
        if (loggable) {
            Log.d(tag, msg);
        }
    }
	
	public static void i(String tag, String msg) {
		if (loggable) {
			Log.i(tag, msg);
		}
	}
	
	public static void w(String tag, String msg) {
		if (loggable) {
			Log.w(tag, msg);
		}
	}
	
	public static void e(String tag, String msg) {
		if (loggable) {
			Log.e(tag, msg);
		}
	}
	
	public static void e(String tag, Throwable throwable) {
		if (loggable) {
			Log.e(tag, throwable.getMessage(), throwable);
		}
	}
	
	public static void e(String tag, String msg, Throwable throwable) {
		if (loggable) {
			Log.e(tag, msg, throwable);
		}
	}

    /**
     * 长日志，以便显示全部的信息
     *
     * @param tag
     * @param tempData 日志内容
     */
    public static void longLog(String tag, String tempData) {
        if (!loggable) {
            return;
        }
        if (TextUtils.isEmpty(tempData)) {
            Log.d(tag, "result is null.");
            return;
        }
        final int len = tempData.length();
        final int div = 2000;
        int count = len / div;
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                Log.d(tag, tempData.substring(i * div, (i + 1) * div));
            }
            int mode = len % div;
            if (mode > 0) {
                Log.d(tag, tempData.substring(div * count, len));
            }
        } else {
            Log.d(tag, tempData);
        }
    }

    /**
     * 将日志写到程序的文件里面
     */
    public static void writeToFile(String msg) {
//        try {
            File fileDir = Envi.appContext.getDir(FILE_DIR_NAME, Context.MODE_PRIVATE);
            String filePath = fileDir.getAbsolutePath() + File.separator + FILE_NAME;
//            StreamUtil.saveStringToFile(msg, filePath);
//        } catch (IOException e) {
//            i("", "书写日志发生错误：" + e.toString());
//        }
    }

    /**
     * 将日志写到SD卡里面
     */
    public static void writeToSDCard(String msg) {
        writeToSDCard(null, msg);
    }

    /**
     * 将日志写到SD卡里面，同时输出log日志
     */
    public static void writeToSDCard(String tag, String msg) {
        if (loggable && msg != null) {
            if (tag != null) {
                Log.i(tag, msg);
                // remove this
                return;
            }

            FileWriter fw = null;
            try {
                File file = new File(Environment.getExternalStorageDirectory() + "/" + FILE_NAME);
                if (!file.exists()) {
                    file.createNewFile();
                } else if (file.length() > 1024 * 1024) {
                    file.delete();
                    file.createNewFile();
                }
                fw = new FileWriter(file, true);
                fw.write(" : " + msg + "\r\n");
//                fw.write(TimeUtil.getTimeStamp() + " : " + msg + "\r\n");
            } catch (Exception e) {
                i("", "书写日志发生错误：" + e.toString());
            } finally {
                try {
                    if (fw != null) {
                        fw.close();
                    }
                } catch (IOException e) {
                }
            }
        }
    }
}
