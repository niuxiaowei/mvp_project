package com.niu.myapp.myapp.view.fragment;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.niu.myapp.myapp.internal.di.HasComponent;
import com.niu.myapp.myapp.presenter.Presenter;
import com.niu.myapp.myapp.view.activity.BaseActivity;
import com.niu.myapp.myapp.view.widget.ConfirmDialogFragment;
import com.niu.myapp.myapp.view.widget.ProgressDialogFragment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**

 */
public abstract class BaseFragment extends Fragment {

    protected BaseActivity mBaseActivity;

    private Set<Presenter> mAllPresenters = new HashSet<Presenter>(1);

    /**
     * 子类调用该方法把一个presenter存入
     * @param presenter
     */
    protected void addPresenter(Presenter presenter){
        mAllPresenters.add(presenter);
    }

    //自定义进度dialog
    private ProgressDialogFragment mProgressDialog;

    private InvokeBuilder mInvokeBuilder;

    public void setInvokeBuilder(InvokeBuilder builder){
        this.mInvokeBuilder = builder;
    }

    public <Data> void invoke(String invokeTag,Data data){
        if(mInvokeBuilder != null){
            mInvokeBuilder.invoke(invokeTag,data);
        }
    }

    /**
     * @param message 进度条显示的信息
     * @param cancelable 点击空白处是否可以取消
     */
    public void showProgressDialog(String message, boolean cancelable){
        /**
         * 为了不重复显示dialog，在显示对话框之前移除正在显示的对话框。
         */
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment fragment = getFragmentManager().findFragmentByTag("progress");
        if (null != fragment) {
            ft.remove(fragment);
        }
        mProgressDialog = ProgressDialogFragment.newInstance(message,cancelable);
        mProgressDialog.show(getFragmentManager(), "progress");
    }


    /**
     * 取消进度条
     */
    public void dissProgressDialog(){
        if(mProgressDialog != null){
            mProgressDialog.dismiss();
        }
    }



    /**
     * 把子类中的presenters加入 父类中，需要调用{@link super.addPresenter()}方法
     */
    protected abstract void onAddPresenters();

    /**
     *初始化presenter，子类只需要 实现，具体调用时机，只需等待即可
     */
    protected abstract void onInitPresenters();

    protected  void onInitViews(){
        for (Presenter presenter:mAllPresenters
                ) {
            if(presenter != null){
                presenter.initView();
            }
        }
    }




    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        onInitializeInjector();
        onInjectFragment();
        onAddPresenters();
        onInitViews();
        onInitPresenters();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof BaseActivity){
            mBaseActivity = (BaseActivity)activity;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (Presenter presenter:mAllPresenters
             ) {
            if(presenter != null){
                presenter.onDestory();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        for (Presenter presenter:mAllPresenters
                ) {
            if(presenter != null){
                presenter.onResume();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        for (Presenter presenter:mAllPresenters
                ) {
            if(presenter != null){
                presenter.onStop();
            }
        }
    }


    /**
     *     //显示确认对话框，dialogId是用来区分不同对话框的

     * @param title 对话框title
     * @param message
     * @param dialogId
     * @param cancelable
     * @param listener
     */
    public void showConfirmDialog(String title,String message,int dialogId,boolean cancelable,ConfirmDialogFragment.ConfirmDialogClickListener listener){

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment fragment = getFragmentManager().findFragmentByTag("confirm");
        if (null != fragment) {
            ft.remove(fragment);
        }
        DialogFragment df = ConfirmDialogFragment.newInstance(title, message, dialogId, cancelable,listener);
        df.show(getFragmentManager(),"confirm");
    }

    /**
     * 初始化注入器,父类会调用该方法，具体实现需要子类来实现
     */
    protected abstract  void onInitializeInjector();

    /**
     * 注入fragment，需要子类来实现,父类会调用该方法
     */
    protected abstract void onInjectFragment();


    /**
     * 该接口定义fragment调用activity的方法
     * @param <Data>
     */
     public static interface FragmentInvokeActivityListener<Data>{
         void invokeActivityMethod(Data data);
     }

    public static class InvokeBuilder{

        private HashMap<String,FragmentInvokeActivityListener> mInvokeListeners ;


        public InvokeBuilder addInvoke(String invokeTag, FragmentInvokeActivityListener listener){
            if(invokeTag == null || listener == null){
                return this;
            }
            if(mInvokeListeners == null){
                mInvokeListeners = new HashMap<>(1);
            }
            mInvokeListeners.put(invokeTag,listener);

            return this;
        }

        public <Data> void invoke(String invokeTag, Data data){
            if(mInvokeListeners != null){
                FragmentInvokeActivityListener l = mInvokeListeners.get(invokeTag);

                l.invokeActivityMethod(data);
            }
        }
    }

}
