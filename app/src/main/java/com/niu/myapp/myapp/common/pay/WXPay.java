package com.niu.myapp.myapp.common.pay;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.niu.myapp.myapp.common.constant.Constant;
import com.niu.myapp.myapp.common.util.DLog;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.modelpay.PayResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;


/**
 * Created by niuxiaowei on 2015/12/23.
 */
public class WXPay extends BasePay<WXPay.WXPayRequest>  {

    private  IWXAPI mPayApi;
    private IPayListener mListener;
    private WXPayRequest mWXPayRequest;

    private static final String EXTRA_PAY_RESULT_KEY = "EXTRA_PAY_RESULT_KEY";

    public static final String ACTION_PAY_RESULT_KEY="ACTION_PAY_RESULT_KEY";

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent != null){
                Bundle args = intent.getBundleExtra(EXTRA_PAY_RESULT_KEY);
                if(args != null){
                    PayResp pr = new PayResp();
                    pr.fromBundle(args);
                    //过滤
                    if(mWXPayRequest != null && pr.prepayId == mWXPayRequest.prepayId){
                        notifyPayResult(pr);

                        //销毁
                        if(mPayApi != null){
                            mPayApi.detach();
                        }
                    }
                }

            }
        }
    };

    /**
     * 发送支付结果的广播
     * @param resp
     */
    public static void sendPayResultBroadcast(BaseResp resp,Context context){
        if(resp != null){
            Intent intent = new Intent(ACTION_PAY_RESULT_KEY);
            Bundle payResult = new Bundle();
            resp.toBundle(payResult);
            intent.putExtra(EXTRA_PAY_RESULT_KEY,payResult);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        }
    }

    public WXPay(Activity activity){
        if(activity != null){
            mPayApi  = WXAPIFactory.createWXAPI(activity, Constant.APP_ID);
            // 将该app注册到微信
            mPayApi.registerApp(Constant.APP_ID);
            //注册广播
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ACTION_PAY_RESULT_KEY);

            LocalBroadcastManager.getInstance(activity).registerReceiver(mReceiver, intentFilter);
        }

    }

    /**
     * 支付方法
     * @param wxPayRequest {@link WXPayRequest}
     */
    @Override
    public void pay(WXPayRequest wxPayRequest,IPayListener listener) {
        this.mWXPayRequest = wxPayRequest;
        mListener = listener;
        if(wxPayRequest != null){
            if(mPayApi != null){
                mPayApi.sendReq(wxPayRequest.toPayReq());
            }
        }
    }

    /**
     * 通知支付结果
     * @param baseResp
     */
    private void notifyPayResult(BaseResp baseResp){
        if(baseResp != null && baseResp.getType()== ConstantsAPI.COMMAND_PAY_BY_WX) {

            String result = "";
            int statuCode = 0;
            switch (baseResp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    result = "支付成功";
                    statuCode = PayResult.RESULT_PAY_OK;
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    result = "用户取消支付";
                    statuCode = PayResult.RESULT_PAY_USER_CANEL;
                    break;
                case BaseResp.ErrCode.ERR_COMM:
                    result = "支付发生错误";
                    statuCode = PayResult.RESULT_PAY_ERROR;
                    break;
                default:
                    result = "发生未知错误";
                    statuCode = PayResult.RESULT_PAY_ERROR;
                    break;
            }
            if(mListener != null){
                mListener.onPayResult(new PayResult(statuCode));
            }
            mListener = null;
            DLog.d("wxpay","支付结果=" + result);
        }

    }





    /**
     * app内部微信支付的请求
     */
    public static class WXPayRequest{
        public String appId;
        public String partnerId;
        public String prepayId;
        public String nonceStr;
        public String timeStamp;
        public String packageValue;
        public String sign;
        public String extData;

        PayReq toPayReq(){
            PayReq req = new PayReq();
            req.appId = appId;
            req.partnerId = partnerId;
            req.prepayId = prepayId;
            req.nonceStr = nonceStr;
            req.timeStamp = timeStamp;
            req.packageValue = packageValue;
            req.sign = sign;
            req.extData = extData;
            return req;
        }
    }




}
