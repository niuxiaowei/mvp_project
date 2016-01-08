package com.niu.myapp.myapp.model.html5;

import android.webkit.JavascriptInterface;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 为js提供的方法是以 key--value的形式存在的，js在使用时会从json中用key值把相应的方法取出来，所以该注解主要是
 * 为了让提供给js的方法与相应的key值相关联
 * Created by niuxiaowei on 2015/10/27.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface JavascriptInterfaceKey {
    String value();
}
