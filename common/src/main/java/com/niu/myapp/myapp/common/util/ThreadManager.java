package com.niu.myapp.myapp.common.util;

/**
 * Created by niuxiaowei on 2015/10/15.
 */
public class ThreadManager {

    public static class CanelableThread extends Thread{

        public void run(){
            interrupt();
        }
    }
}
