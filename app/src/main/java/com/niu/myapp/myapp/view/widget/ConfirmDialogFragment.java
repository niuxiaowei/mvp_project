package com.niu.myapp.myapp.view.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.niu.myapp.myapp.R;

/**
 * Created by niuxiaowei on 2015/10/16.
 */
public class ConfirmDialogFragment extends BaseDialogFragment {

    private ConfirmDialogClickListener mListener;
    public static ConfirmDialogFragment newInstance(String title, String message,int dialogId,boolean isCanelable,ConfirmDialogClickListener listener){
        ConfirmDialogFragment instance = new ConfirmDialogFragment();
        Bundle args = new Bundle();
        putIdParam(args,dialogId);
        putTitleParam(args, title);
        putMessageParam(args, message);
        putCancelableParam(args, isCanelable);
        instance.setArguments(args);
        instance.mListener = listener;
        return instance;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if(!mIsCustomDialog){

            String title = parseTitleParam();
            String message = parseMessageParam();
            AlertDialog dialog = new AlertDialog.Builder(getActivity()).setTitle(title== null?getString(R.string.app_name):title).setMessage(message== null?" ":message)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            if(mListener != null){
                                mListener.onOKClick();

                            }
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(mListener != null) {
                                mListener.onCancleClick();

                            }
                        }
                    }).create();
            return dialog;
        }else{
            return super.onCreateDialog(savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mIsCustomDialog){
            View view = inflater.inflate(R.layout.dialog_custom_progress,container,false);
            //启用窗体的扩展特性。
            getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
            return view;
        }else{
            return super.onCreateView(inflater,container,savedInstanceState);
        }
    }

    public static interface ConfirmDialogClickListener{
        void onOKClick();
        void onCancleClick();
    }
}
