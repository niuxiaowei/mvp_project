package com.niu.myapp.myapp.common.rxbus;


import com.niu.myapp.myapp.common.util.DLog;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import rx.Observable;


/**
 * RXBus管理类，会管理app启动的所有的RXBus，
 * Created by niuxiaowei on 2016/3/4.
 */
public class RXBusManager {

    private static RXBusManager mRXBusManager = new RXBusManager();
    private ConcurrentHashMap<String, RxBus> mAllRXBuses = new ConcurrentHashMap<String,RxBus>(1);

    private RXBusManager() {

    }


    public static RXBusManager getInstance() {
        return mRXBusManager;
    }

    /**
     * 发送一个事件
     * @param eventType 事件类型
     * @param eventValue
     */
    public void send(String eventType,Object eventValue) {
        DLog.i(RXBusManager.class.getName(), " send eventtype=" + eventType + " eventValue=" + eventValue);
        if (eventValue != null) {
            RxBus bus = mAllRXBuses.get(eventValue.getClass().getName());
            if (bus != null) {
                if (bus.hasObservers()) {

                    bus.send(eventValue);
                }else{
                    mAllRXBuses.remove(eventValue.getClass().getName());
                }
            }
        }
    }

    public Observable<Object> toObserverable(String eventType) {
        DLog.i(RXBusManager.class.getName(), " toObserverable eventtype=" + eventType);
        RxBus bus = null;
        if (eventType != null) {

            bus = mAllRXBuses.get(eventType);
            if(bus == null){
                bus = new RxBus();
                mAllRXBuses.put(eventType,bus);
            }
            return bus.toObserverable();
        }
        return  null;
    }


}
