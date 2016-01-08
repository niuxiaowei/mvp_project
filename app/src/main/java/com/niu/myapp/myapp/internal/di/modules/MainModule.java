package com.niu.myapp.myapp.internal.di.modules;

import com.niu.myapp.myapp.internal.di.PerActivity;
import com.niu.myapp.myapp.presenter.MainPresenter;
import com.niu.myapp.myapp.view.compnent.IMainView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by niuxiaowei on 2015/11/18.
 */
@Module
public class MainModule {

    private IMainView mMainView;

    public MainModule(IMainView mainView){
        this.mMainView = mainView;
    }

    @Provides @PerActivity
    MainPresenter provideMainPresenter(){
        return new MainPresenter(mMainView);
    }


}
