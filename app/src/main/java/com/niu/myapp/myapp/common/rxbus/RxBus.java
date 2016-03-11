package com.niu.myapp.myapp.common.rxbus;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * courtesy: https://gist.github.com/benjchristensen/04eef9ca0851f3a5d7bf
 */
public class RxBus {

    //private final PublishSubject<Object> mRXBus = PublishSubject.create();

    // If multiple threads are going to emit events to this
    // then it must be made thread-safe like this instead
    final Subject<Object, Object> mRXBus = new SerializedSubject<>(PublishSubject.create());

    RxBus(){

    }

    void send(Object o) {
            mRXBus.onNext(o);
    }

    Observable<Object> toObserverable() {


        return mRXBus;
    }

    boolean hasObservers() {
        return mRXBus.hasObservers();
    }
}