package com.niu.myapp.myapp.view.widget;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.niu.myapp.myapp.view.activity.BaseActivity;

/**
 * Created by niuxiaowei on 2015/10/15.
 * 自定义dialog，是所有自定义dialog的基类
 */
public class BaseDialogFragment extends DialogFragment {

    protected BaseActivity mBaseActivity;


    private static final String EXTRA_DIALOG_TITLE_KEY = "extra_dialog_title_key";
    private static final String EXTRA_DIALOG_MESSAGE_KEY = "extra_dialog_message_key";
    private static final String EXTRA_DIALOG_CANELABLE_KEY = "extra_dialog_cancelable";
    private static final String EXTRA_DIALOG_IS_CUSTOM_KEY = "extra_dialog_is_custom";
    private static final String EXTRA_DIALOG_ID_KEY = "extra_dialog_id";

    //是否是自定义dialog
    protected boolean mIsCustomDialog = false;
    //每个对话框的ID
    protected int mDialogId;





    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        parseIdParam();
        if(getActivity() instanceof BaseActivity){
            mBaseActivity = (BaseActivity)getActivity();
        }
    }

    protected  static void putIdParam(Bundle args,int dialogId){
        if(args != null){
            args.putInt(EXTRA_DIALOG_ID_KEY,dialogId);
        }
    }

    protected static void putIsCustomParam(Bundle args,boolean isCustomDialog){
        if(args != null){
            args.putBoolean(EXTRA_DIALOG_IS_CUSTOM_KEY,isCustomDialog);
        }
    }

    protected  static void  putTitleParam(Bundle bundler,String title){

        bundler.putString(EXTRA_DIALOG_TITLE_KEY,title);
    }

    protected static  void putCancelableParam(Bundle bundle,boolean cancelable){
        if(bundle != null){
            bundle.putBoolean(EXTRA_DIALOG_CANELABLE_KEY,cancelable);
        }
    }

    protected static void  putMessageParam(Bundle bundler,String message){

        bundler.putString(EXTRA_DIALOG_MESSAGE_KEY,message);
    }

    protected  boolean parseCancelableParam(){
        Bundle bundle = getArguments();
        if(bundle != null){
            return bundle.getBoolean(EXTRA_DIALOG_CANELABLE_KEY);
        }
        return false;
    }

    protected   String parseMessageParam(){
        Bundle bundle = getArguments();
        if(bundle == null){
            return null;
        }
        return bundle.getString(EXTRA_DIALOG_MESSAGE_KEY);
    }

    protected  String parseTitleParam(){
        Bundle bundle = getArguments();
        if(bundle == null){
            return null;
        }
        return  bundle.getString(EXTRA_DIALOG_TITLE_KEY);
    }

    protected void parseIdParam(){
        Bundle args = getArguments();
        if(args != null){
            mDialogId = args.getInt(EXTRA_DIALOG_ID_KEY);
        }
    }

    protected  void parseIsCustomParam(Bundle args){
        if(args != null){
            mIsCustomDialog = args.getBoolean(EXTRA_DIALOG_IS_CUSTOM_KEY);
        }
    }

//    public BaseDialogFragment(Context context) {
//        super(context);
//        Window win = getWindow();
//        win.requestFeature(Window.FEATURE_NO_TITLE);
//        win.setBackgroundDrawableResource(android.R.color.transparent);
//        win.getAttributes().width = WindowManager.LayoutParams.MATCH_PARENT;
//    }




}
