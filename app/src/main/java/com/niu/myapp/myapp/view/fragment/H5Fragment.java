package com.niu.myapp.myapp.view.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.TextView;

import com.niu.myapp.myapp.R;
import com.niu.myapp.myapp.common.util.ToastUtil;
import com.niu.myapp.myapp.internal.di.components.DaggerH5Component;
import com.niu.myapp.myapp.internal.di.components.H5Component;
import com.niu.myapp.myapp.internal.di.modules.H5Module;
import com.niu.myapp.myapp.data.jsdata.JavaJSBridge;
import com.niu.myapp.myapp.data.jsdata.JavascriptInterfaceKey;
import com.niu.myapp.myapp.presenter.H5Presenter;
import com.niu.myapp.myapp.view.compnent.IH5View;

import java.util.HashSet;

import javax.inject.Inject;

/**
 * Created by niuxiaowei on 2015/11/24.
 */
public class H5Fragment extends BaseFragment implements IH5View, H5Presenter.IJavaJSInterfaces {

    public static final String EXTRA_WEB_URL_KEY = "web_url";
    @Inject
    H5Presenter mPresenter;
    private WebView mWebView;
    private H5Component mH5Component;
    private TextView mTitleView;
    private String mUrl;

    @Override
    protected void onAddPresenters() {
        addPresenter(mPresenter);
    }

    @Override
    protected void onInitPresenters() {

        mPresenter.init(mWebView, this);
    }


    @Override
    protected void onInjectFragment() {
        mH5Component = DaggerH5Component.builder().applicationComponent(mBaseActivity.getApplicationComponent()).h5Module(new H5Module()).build();
        mH5Component.inject(this);
    }

    @Override
    public void setTitleForView(String title) {
        mTitleView.setText(title);

    }

    @Override
    public void onHtml5PageIsOK() {
        if (mPresenter != null) {

            mPresenter.getTitle();
        }
    }

    @Override
    public void onCall(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
        startActivity(intent);
    }

    @Override
    public void bindDataForView(Object o) {

    }

    @Override
    public void initView() {
        mTitleView = (TextView) getView().findViewById(R.id.title);

        //将WebView实例化
        mWebView = (WebView) getView().findViewById(R.id.webview);


        parseArgs();
        if (mUrl != null) {
            mWebView.loadUrl(mUrl);
        }

    }

    /**
     * 解析别的页面传入的参数
     */
    private void parseArgs() {
        Bundle args = getArguments();
        if (args != null) {
            mUrl = args.getString(EXTRA_WEB_URL_KEY);

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_h5, container, false);
    }

    @Override
    public HashSet<JavaJSBridge.JavaJSInterfaces> onCreateJavaJSInterfaces() {
        HashSet<JavaJSBridge.JavaJSInterfaces> result = new HashSet<>(2);
        result.add(new JavaJSBridge.JavaJSInterfaces("navi") {

            /**
             * js调用native进行导航
             * @param lat 纬度
             * @param lng 经度
             */
            @JavascriptInterfaceKey("onNavi")
            @JavascriptInterface
            public void onNavi(String lat, String lng) {
                ToastUtil.showLong(mBaseActivity, "开始导航 lat=" + lat + " lng=" + lng);
            }

        });

        return result;
    }
}
