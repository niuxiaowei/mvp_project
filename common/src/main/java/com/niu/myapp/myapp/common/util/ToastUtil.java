package com.niu.myapp.myapp.common.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by niuxiaowei on 2015/10/16.
 * toast工具類
 */
public class ToastUtil {
    public static boolean isShow = true;
    //當前正在顯示的toast
    private static Toast sCurrentToast;

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message)
    {
        cancelCurrentToast();
        if (isShow){
            sCurrentToast =  Toast.makeText(context, message, Toast.LENGTH_SHORT);
            sCurrentToast.show();
        }

    }

    //取消當前正在顯示的toast
    private static void cancelCurrentToast(){
        if(sCurrentToast != null){
            sCurrentToast.cancel();
        }
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, int message)
    {
        cancelCurrentToast();
        if (isShow){

            sCurrentToast =Toast.makeText(context, message, Toast.LENGTH_SHORT);
            sCurrentToast.show();
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message)
    {
        cancelCurrentToast();
        if (isShow){

           sCurrentToast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            sCurrentToast.show();
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, int message)
    {
        cancelCurrentToast();
        if (isShow){

           sCurrentToast =  Toast.makeText(context, message, Toast.LENGTH_LONG);
            sCurrentToast.show();
        }
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, CharSequence message, int duration)
    {
        cancelCurrentToast();
        if (isShow){

            sCurrentToast =  Toast.makeText(context, message, duration);
            sCurrentToast.show();
        }

    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, int message, int duration)
    {
        cancelCurrentToast();
        if (isShow){
           sCurrentToast =  Toast.makeText(context, message, duration);

            sCurrentToast.show();
        }
    }

    public static void desotry(){
        sCurrentToast = null;
    }
}
