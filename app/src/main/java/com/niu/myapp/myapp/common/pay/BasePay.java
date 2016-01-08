package com.niu.myapp.myapp.common.pay;



/**
 * 支付的基础类，微信支付，支付宝支付等等其他的支付方式都可继承此支付类
 * Created by niuxiaowei on 2015/12/23.
 */
public abstract class BasePay<T> {


    /**
     * 支付结果回调
     */
    public static interface IPayListener{
        void onPayResult(PayResult payResult);
    }

    /**
     * 支付结果封装类
     */
    public static class PayResult{
        public final static int RESULT_PAY_OK = 0;
        public final static int RESULT_PAY_USER_CANEL = -2;
        public final static int RESULT_PAY_ERROR = -1;

        public int mResultCode;

        public PayResult(int code){
            this.mResultCode = code;
        }
    }

    /**
     * 支付方法
     * @param t 支付请求数据
     * @param listener {@link IPayListener}支付的回调接口
     */
    public abstract void pay(T t,IPayListener listener);
}
