-renamesourcefileattribute ProGuard
#-printusage unused.txt #把没有混淆的类写入制定文件
#-applymapping mapping_previous.txt
-keepattributes SourceFile,LineNumberTable,*Annotation*,Signature,InnerClasses,Exceptions
-optimizationpasses 1 # 指定代码的压缩级别
-dontusemixedcaseclassnames # 是否使用大小写混合
-dontskipnonpubliclibraryclasses # 是否混淆第三方jar
-dontpreverify # 混淆时是否做预校验
-verbose # 混淆时是否记录日志
#优化算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#缺省proguard 会检查每一个引用是否正确，但是第三方库里面往往有些不会用到的类，没有正确引用。如果不配置的话，系统就会报错
-dontwarn android.support.v4.**
-dontwarn android.support.v13.**
-dontwarn android.webkit.WebViewClient
-dontwarn com.ganji.android.lib.util.UmengWrapper



-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.view.View


-keepattributes JavascriptInterface
-keepattributes *Annotation*

# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclassmembers class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator CREATOR;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

-keepclassmembers class * extends java.io.Serializable {
    private static final long serialVersionUID;
}
#keepclassmember keepclasswithmember与keepclassmembernames keepclasswithmembernames区别在于前者在进行压缩时，肯定不会被删除掉。后者有可的能会被删除掉。。
#对于keepclassmember keepclasswithmember keepclassmembernames keepclasswithmembernames，必须在{}中加入相应的属性，这些被加入的属性才会被保护
-keepclassmember public class  com.niu.myapp.buildproductvariant.data.UserInfo{
   public <methods>;
}

#所有的注解类
-keepclasseswithmembers public @interface *{
  public <methods>;
 }
#对于不确定的包 用2个**号
#保护所有用JavascriptInterfaceKey注解的方法
-keepclassmembers public class *{

  @**.JavascriptInterfaceKey public *;
}

