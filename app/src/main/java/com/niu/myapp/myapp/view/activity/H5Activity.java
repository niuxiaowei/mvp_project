package com.niu.myapp.myapp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.niu.myapp.myapp.R;
import com.niu.myapp.myapp.common.util.DLog;
import com.niu.myapp.myapp.common.util.ToastUtil;
import com.niu.myapp.myapp.presenter.H5Presenter;
import com.niu.myapp.myapp.view.compnent.IH5View;
import com.niu.myapp.myapp.view.fragment.H5Fragment;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by niuxiaowei on 2015/10/22.
 */
public class H5Activity extends BaseActivity {



    /**
     * 跳入html5界面
     * @param context
     * @param url 跳入网页的地址
     */
    public static void toH5Activity(Context context, String url){
        Intent toH5Intent = new Intent(context,H5Activity.class);
        toH5Intent.putExtra(H5Fragment.EXTRA_WEB_URL_KEY,url);
        context.startActivity(toH5Intent);
    }





    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h5);


        FragmentManager fm = getSupportFragmentManager();
        Fragment   fragment = new H5Fragment();
        fragment.setArguments(getIntent().getExtras());
        /*
        下面的代码，第一个参数 是 即将作为fragment的容器的容器id，但同时也得配合下面的代码才能生效，
        *public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        * 意思是当前inflate出来的view不是rootview
        return inflater.inflate(R.layout.fragment_h5,container,false);
    }
        *
         */
        fm.beginTransaction().add(R.id.container,fragment).commit();




    }




}
