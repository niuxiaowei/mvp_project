//package com.niu.myapp.myapp.wxapi;
//
//import android.content.Intent;
//import android.os.Bundle;
//
//import com.niu.myapp.myapp.R;
//import com.niu.myapp.myapp.common.constant.Constant;
//import com.niu.myapp.myapp.view.activity.BaseActivity;
//
//import com.tencent.mm.sdk.modelbase.BaseReq;
//import com.tencent.mm.sdk.modelbase.BaseResp;
//import com.tencent.mm.sdk.openapi.IWXAPI;
//import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
//import com.tencent.mm.sdk.openapi.WXAPIFactory;
//
///**
// * Created by niuxiaowei on 2015/12/24.
// */
//public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {
//
//    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
//
//    private IWXAPI api;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_wx_pay_result_layout);
//
//
//        api = WXAPIFactory.createWXAPI(this, Constant.APP_ID);
//        api.handleIntent(getIntent(), this);
//    }
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        setIntent(intent);
//        api.handleIntent(intent, this);
//    }
//
//    @Override
//    public void onReq(BaseReq req) {
//    }
//
//    @Override
//    public void onResp(BaseResp resp) {
//        WXPay.sendPayResultBroadcast(resp, this);
//    }
//}
