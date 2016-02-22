package com.niu.myapp.myapp.view.fragment;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.niu.myapp.myapp.presenter.Presenter;
import com.niu.myapp.myapp.view.activity.BaseActivity;
import com.niu.myapp.myapp.view.util.Functions;
import com.niu.myapp.myapp.view.widget.BaseDialogFragment;
import com.niu.myapp.myapp.view.widget.DialogFactory;

import java.util.HashSet;
import java.util.Set;

/**

 */
public abstract class BaseFragment extends Fragment {

    protected BaseActivity mBaseActivity;

    protected DialogFactory mDialogFactory ;

    private Set<Presenter> mAllPresenters = new HashSet<Presenter>(1);


    /**
     * 子类调用该方法把一个presenter存入
     * @param presenter
     */
    protected void addPresenter(Presenter presenter){
        mAllPresenters.add(presenter);
    }



    protected Functions mFunctions;

    public void setFunctions(Functions functions){
        this.mFunctions = functions;
    }

    public BaseDialogFragment.BaseDialogListener getDialogListener(){
        return mDialogFactory.mListenerHolder.getDialogListener();
    }

    /**
     * 清空DialogListenerHolder中的dialog listener
     */
    public void clearDialogListener(){
        mDialogFactory.mListenerHolder.setDialogListener(null);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mDialogFactory.mListenerHolder.saveDialogListenerKey(outState);
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDialogFactory = new DialogFactory(getChildFragmentManager(),savedInstanceState);
        mDialogFactory.restoreDialogListener(this);
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
            mBaseActivity.setFunctionsForFragment(getId());
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
     * 初始化注入器,父类会调用该方法，具体实现需要子类来实现
     */
    protected abstract  void onInitializeInjector();

    /**
     * 注入fragment，需要子类来实现,父类会调用该方法
     */
    protected abstract void onInjectFragment();




}
