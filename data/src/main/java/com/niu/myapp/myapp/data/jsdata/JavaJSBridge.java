package com.niu.myapp.myapp.data.jsdata;

import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.niu.myapp.myapp.common.util.DLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by niuxiaowei on 2015/10/26.
 */
public  class JavaJSBridge{


    //js提供给java调用的接口
    private JSONObject sJavaInterfaces;

    //java提供给js调用的接口
    private  JSONObject sJavascriptInterfaces;

    //存储还没执行完毕的jsrequest，若一次js request执行完毕，则会从该容器中移除掉
    private List<JSRequest> mJSRequests = new ArrayList<JSRequest>();


    private WebView mWebView;

    //初始化js的接口，在初始化js后，js会调用java的初始化方法，来进行初始化
    private static final String JAVA_INTERFACE_ON_INIT_KEY = "onInit";




    /**
     * @param webView
     * @param bridgeCallback
     * @param allJavascriptInterfaces 调用{@link JavascriptInterfacesBuilder}进行生成
     */
    public JavaJSBridge(WebView webView ,  final IBridgeListener bridgeListener,Set<JavaJSInterfaces> allJavascriptInterfaces){
        this.mWebView = webView;


        if(allJavascriptInterfaces == null){
            allJavascriptInterfaces = new HashSet<>(1);
        }
        //把初始化方法加入
        allJavascriptInterfaces.add(new JavaJSInterfaces("init") {


            //初始化java的方法，js会主动调用该方法，把js提供给java调用的接口传入
            @JavascriptInterface
            @JavascriptInterfaceKey(value = JAVA_INTERFACE_ON_INIT_KEY)
            public void onInit(String jsInterfaces) {
                try {
                    sJavaInterfaces = new JSONObject(jsInterfaces);
                    DLog.i("test", "js 传给java的接口=" + sJavaInterfaces.toString());

                    if (bridgeListener != null) {
                        bridgeListener.onBridgePrepared();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });


        try {
            genJavascriptInterfacesJson(allJavascriptInterfaces);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 生成java为JavaScript提供的所有接口的json串
     * @param allJavascriptInterfaces 为js提供的接口集合
     * @return
     */
    private void genJavascriptInterfacesJson(Set<JavaJSInterfaces> allJavascriptInterfaces) throws Exception {
        //把所有提供的方法注入webview，同时生成协议json串，传递给js
        Iterator<JavaJSInterfaces> it = allJavascriptInterfaces.iterator();
        sJavascriptInterfaces = new JSONObject();
        while(it.hasNext()){
            JavaJSInterfaces temp = it.next();
            if(temp != null){
                if(mWebView != null){
                    if(temp != null){
                        /*把对象注入js中*/
                        mWebView.addJavascriptInterface(temp,temp.mJSName);

                    }
                }

                try {

                    /*提供给js的方法时否用JavascriptInterfaceKey注解标注了*/
                    boolean hasJavascriptInterfaceKeyAnnoOnMethod = false;
                    //把java提供给js调用的接口放到json中
                    Class bridgeClass = temp.getClass();
                    Method[] allMethod = bridgeClass.getDeclaredMethods();
                    for (Method method:allMethod){
                        //说明这是提供给js的接口
                        if(method.getAnnotation(JavascriptInterface.class) != null){
                            /*既然是提供给js的接口就得用JavascriptInterfaceKey标注，否则报错*/
                            JavascriptInterfaceKey jsKey = method.getAnnotation(JavascriptInterfaceKey.class);
                            if(jsKey != null){
                                String key = jsKey.value();
                                String methodName = method.getName();
                                Class[] parameters = method.getParameterTypes();
                                String params = "";
                                for (int i = 0; i <parameters.length; i++){
                                    if(i == parameters.length - 1){

                                        params = params +"arg"+i;
                                    }else{

                                        params = params +"arg"+i+",";
                                    }
                                }
                                sJavascriptInterfaces.put(key, "function " + key + "(" + params + "){ window." + temp.mJSName + "." + methodName + "(" + params + ");}");
                            }else{
                                throw new Exception("提供给js的接口必须用JavascriptInterfaceKey进行标注");
                            }

                        }
//                        Annotation[] annos = method.getDeclaredAnnotations();
//                        for(Annotation ann:annos){
//                            if(ann instanceof JavascriptInterfaceKey){
//
//                            }
//                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    public void execuJSRequest(String jsInterfaceKey, String callbackKey, Object[] params){
        JSRequest request = new JSRequest();
        request.mJSInterfaceKey = jsInterfaceKey;
        request.mJavaInterfaceKey = callbackKey;
        request.mParams = params;
        execuJSRequest(request);
    }


    //执行jsrequest
    private void execuJSRequest(JSRequest request){

        if(request == null){
            DLog.i("test","当前请求的request为空");
            return;
        }
        String jsInterface = request.getJSInterface();
        if(jsInterface != null){

            invokeJSMethod(jsInterface);
//            synchronized (mJSRequests){
//                mJSRequests.add(request);
//            }
        }

    }

    //调用js的方法
    private void invokeJSMethod(String jsMethod){
        DLog.i("test", "开始调用js的 " + jsMethod + " 方法");
        mWebView.loadUrl(jsMethod);
    }

    //初始化js
    public void initJS(){


        if(sJavascriptInterfaces != null){
            String callback = sJavascriptInterfaces.optString(JAVA_INTERFACE_ON_INIT_KEY);
            invokeJSMethod("javascript:onInit(" + sJavascriptInterfaces + "," + callback + ")");
            DLog.i("test","初始化js");
        }
    }









    /**
     * Created by niuxiaowei on 2015/10/26.
     *
     * 调用js接口的request类，
     */
    private class JSRequest {


        public String mJSInterfaceKey;

        private Object[] mParams;
        //java为js提供的接口key值
        public String mJavaInterfaceKey;


        //获取js调用的接口
         String getJSInterface(){

            if(sJavaInterfaces == null){
                return null;
            }
            String jsMethod =  sJavaInterfaces.optString(mJSInterfaceKey);
            if(jsMethod == null || jsMethod.trim().equals("")){
                return null;
            }

            //为调用的js方法 拼接 javascript:关键字段
            if(jsMethod.indexOf("javascript:",0) != 0){
                jsMethod = "javascript:"+jsMethod;
            }

            if(mParams != null){

                //对是字符串类型的参数，用单引号包裹起来，否则js不认识该参数
                for(int i=0; i < mParams.length; i++){
                    if(mParams[i] != null){
                        if(mParams[i] instanceof String){
                            //用单引号把参数包裹起来
                            mParams[i] = "'"+mParams[i]+"'";
                        }
                    }
                }
            }

            //取java提供的回调方法
            String javaCallbackMethod = null;
            if(sJavascriptInterfaces != null){

                javaCallbackMethod  = sJavascriptInterfaces.optString(mJavaInterfaceKey);
            }


            //开始进行format
            String result = jsMethod;
            if(mParams != null ){
                result = String.format(jsMethod,mParams);
            }
            if(javaCallbackMethod != null){
                result = String.format(result,javaCallbackMethod);
            }
            return result;
        }



    }

    /**
     * 辅助创建JavaJSInterfaces的builder类
     */
    public static class JavascriptInterfacesBuilder{
        private HashSet<JavaJSInterfaces> mAllInterfaces = new HashSet<JavaJSInterfaces>(3);


        public JavascriptInterfacesBuilder addJavascriptInterfaces(JavaJSInterfaces interfaces){
            if(interfaces != null){
                mAllInterfaces.add(interfaces);
            }
            return this;
        }

        public JavascriptInterfacesBuilder addJavascriptInterfaces(HashSet<JavaJSInterfaces> interfaces){
            if(interfaces != null && interfaces.size() > 0){
                mAllInterfaces.addAll(interfaces);
            }
            return this;
        }

        /**
         * 当添加JavaJSInterfaces完毕时，调用该方法
         * @return
         */
        public Set<JavaJSInterfaces> createJavascriptInterfaces(){
            return mAllInterfaces;
        }


    }


    /**
     * 本类是 java与JavaScript进行交互时各种接口定义 的基类，本类的子类中可以定义多个接口(可以定义java调用js的接口，也可以定义js回调java的接口）。
     * {@value mJSName}是注入js时，本对象实例对应的名字(mWebView.addJavascriptInterface(object,name))<br>
     * 具体例子:<br>
     *
     * new JavaJSInterfaces("sample"){
     *
     *      //java提供给js的接口必须得有下面的两个注解
     *      @JavascriptInterfaceKey("onNavi")  @JavascriptInterfaceKey这注释必须得有，本注释的value值是与js之间定义好的值，一个value值代表一个接口
     *      @JavascriptInterface
     *      public void onNavi(String lat, String lng){
     *          ToastUtil.showLong(mBaseActivity,"开始导航 lat="+lat+" lng="+lng);
     *     }
     *
     *     //js提供的获取title方法的  对应的key值
            private final String JS_INTERFACE_GET_TITLE_KEY = "getTitle";
            private final String JAVA_INTERFACE_ON_GET_TITLE_KEY = "onGetTitle";

            //java调用js的接口不需要用注解
            public void getTitle() {
                execuJSRequest(JS_INTERFACE_GET_TITLE_KEY, JAVA_INTERFACE_ON_GET_TITLE_KEY, null);
            }

            @JavascriptInterface
            @JavascriptInterfaceKey(value = JAVA_INTERFACE_ON_GET_TITLE_KEY)
            public void onGetTitle(final String title) {
                UIThreadExecutor.getExecutor().executTask(new Runnable() {
                    @Override
                    public void run() {
                        if (mIH5View != null) {
                            mIH5View.setTitleForView(title);
                        }
                    }
                });
            }
     * }
     */
    public static abstract class JavaJSInterfaces {

        public String mJSName;

        public JavaJSInterfaces(String jSName) {
            super();
            this.mJSName = jSName;
        }



        /**
         * 该方法是为解决在调用 mWebView.addJavascriptInterface(Object,name)方法的时候 报错问题
         */
        @JavascriptInterface
        public void neverUsedMethod() {


        }

        @Override
        public boolean equals(Object o) {
            if(o != null && o instanceof JavaJSInterfaces){
                JavaJSInterfaces temp = (JavaJSInterfaces)o;
                return mJSName != null? mJSName.equals(temp.mJSName):false;
            }
            return super.equals(o);
        }




    }

    //定义java供js使用的接口
    public static interface IBridgeListener{



        //桥接器已经准备好了
        void onBridgePrepared();

    }



}
