package com.niu.myapp.myapp.presenter;

import android.graphics.Bitmap;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.niu.myapp.myapp.base.present.BasePresenter;
import com.niu.myapp.myapp.base.present.Presenter;
import com.niu.myapp.myapp.common.executor.NormalThreadExecutor;
import com.niu.myapp.myapp.common.executor.UIThreadExecutor;
import com.niu.myapp.myapp.common.util.DLog;
import com.niu.myapp.myapp.data.jsdata.JavaJSBridge;
import com.niu.myapp.myapp.data.jsdata.JavascriptInterfaceKey;
import com.niu.myapp.myapp.view.compnent.IH5View;

import java.util.HashSet;

import javax.inject.Inject;


/**
 * Created by niuxiaowei on 2015/10/23.
 * html5的presenter主持类，涉及到html5与nativ之间的交互
 */
public class  H5Presenter  extends BasePresenter implements Presenter,JavaJSBridge.IBridgeListener {

    private WebView mWebView;
    private IH5View mIH5View;
    private JavaJSBridge mBridge ;

    private IJavaJSInterfaces mIJavascriptInterfaces;

    @Inject
    public H5Presenter(UIThreadExecutor uiThreadExecutor, NormalThreadExecutor normalThreadExecutor){
        super(uiThreadExecutor,normalThreadExecutor);
    }

    private CommonJavascriptInterfaces mCommonJSInterfaces = new CommonJavascriptInterfaces("common");

    @Override
    public void onBridgePrepared() {

        mUIThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mIH5View.onHtml5PageIsOK();
            }
        });
    }


    /**
     * 为js定义通用的接口
     */
    public   class CommonJavascriptInterfaces extends JavaJSBridge.JavaJSInterfaces {

        //从js处获取当前页面的title的方法，因为执行java与js代码是分别在不同的线程中的，所以基本每个方法都会对应自己的回调方法

        //js提供的获取title方法的  对应的key值
        private final String JS_INTERFACE_GET_TITLE_KEY = "getTitle";
        private final String JAVA_INTERFACE_ON_GET_TITLE_KEY = "onGetTitle";

        public CommonJavascriptInterfaces(String jSName) {
            super(jSName);
        }

        public void getTitle() {


            execuJSRequest(JS_INTERFACE_GET_TITLE_KEY, JAVA_INTERFACE_ON_GET_TITLE_KEY, null);
        }

        @JavascriptInterface
        @JavascriptInterfaceKey(value = JAVA_INTERFACE_ON_GET_TITLE_KEY)
        public void onGetTitle(final String title) {
            mUIThreadExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    if (mIH5View != null) {
                        mIH5View.setTitleForView(title);
                    }
                }
            });
        }

        //js调用native的打电话功能
        private final String JAVA_INTERFACE_ON_CALL_KEY = "onCall";

        //js调用native打电话功能
        @JavascriptInterface
        @JavascriptInterfaceKey(value = JAVA_INTERFACE_ON_CALL_KEY)
        public void onCall(final String phoneNum) {

            mUIThreadExecutor.execute(new Runnable() {
                @Override
                public void run() {

                    if (mIH5View != null) {
                        mIH5View.onCall(phoneNum);
                    }
                }
            });
        }
    }





    public void initView(IH5View ih5View) {
        this.mIH5View = ih5View;
        mIH5View.initView();
    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestory() {

    }


    /**
     * 进行初始化工作
     * @param webview
     * @param iJavascriptInterfaces 创建JavaScript接口
     */
    public void init(WebView webview,IJavaJSInterfaces iJavascriptInterfaces){

        //注意  在给webview注入对象的时机必须在对webview进行设置之前，否则不行
        this.mWebView = webview;

        this.mIJavascriptInterfaces = iJavascriptInterfaces;


        /*初始化bridge*/
        mBridge = new JavaJSBridge(mWebView,this,new JavaJSBridge.JavascriptInterfacesBuilder()
                .addJavascriptInterfaces(mCommonJSInterfaces)
                .addJavascriptInterfaces(mIJavascriptInterfaces != null?mIJavascriptInterfaces.onCreateJavaJSInterfaces():null)
                .createJavascriptInterfaces());



        /*初始化webview*/
        //WebView打开权限
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        //使得获取焦点以后可以使用软键盘
        mWebView.requestFocusFromTouch();
        //允许使用JAVASCRIPT脚本

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
//                DLog.e("jsdata", "webview receive error: " + description);
                super.onReceivedError(view, errorCode, description, failingUrl);
//                mWebViewReceivedError = true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                DLog.i("test", "初始化javascript");
               mBridge.initJS();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                DLog.i("test", "alert  url=" + url + " message=" + message + " result=" + result.toString());
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }

            @Override
            public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
                return super.onJsBeforeUnload(view, url, message, result);
            }
        });
    }

    /**
     * 获取h5页面的title
     */
    public void getTitle() {

        if(mCommonJSInterfaces != null){
            mCommonJSInterfaces.getTitle();
        }
    }

    /**
     * 该接口主要用来进行扩展，定义java为JavaScript提供的接口
     */
    public static interface IJavaJSInterfaces {
        /**
         * 获取java为js提供的接口,具体调用时机底层会做
         * @return HashSet<JavaJSBridge.JavaJSInterfaces> java为javascript提供的接口的集合
         */
        HashSet<JavaJSBridge.JavaJSInterfaces> onCreateJavaJSInterfaces();
    }

    /**
     * @param jsInterfaceKey
     * @param callbackKey
     * @param params
     */
    public void execuJSRequest(String jsInterfaceKey, String callbackKey, Object[] params){
        if(mBridge != null){
            mBridge.execuJSRequest(jsInterfaceKey,callbackKey,params);
        }
    }



}
