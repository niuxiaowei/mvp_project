-renamesourcefileattribute ProGuard
#-printusage unused.txt #��û�л�������д���ƶ��ļ�
#-applymapping mapping_previous.txt
-keepattributes SourceFile,LineNumberTable,*Annotation*,Signature,InnerClasses,Exceptions
-optimizationpasses 1 # ָ�������ѹ������
-dontusemixedcaseclassnames # �Ƿ�ʹ�ô�Сд���
-dontskipnonpubliclibraryclasses # �Ƿ����������jar
-dontpreverify # ����ʱ�Ƿ���ԤУ��
-verbose # ����ʱ�Ƿ��¼��־
#�Ż��㷨
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#ȱʡproguard ����ÿһ�������Ƿ���ȷ�����ǵ�����������������Щ�����õ����࣬û����ȷ���á���������õĻ���ϵͳ�ͻᱨ��
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
#keepclassmember keepclasswithmember��keepclassmembernames keepclasswithmembernames��������ǰ���ڽ���ѹ��ʱ���϶����ᱻɾ�����������пɵ��ܻᱻɾ��������
#����keepclassmember keepclasswithmember keepclassmembernames keepclasswithmembernames��������{}�м�����Ӧ�����ԣ���Щ����������ԲŻᱻ����
-keepclassmember public class  com.niu.myapp.buildproductvariant.data.UserInfo{
   public <methods>;
}

#���е�ע����
-keepclasseswithmembers public @interface *{
  public <methods>;
 }
#���ڲ�ȷ���İ� ��2��**��
#����������JavascriptInterfaceKeyע��ķ���
-keepclassmembers public class *{

  @**.JavascriptInterfaceKey public *;
}

