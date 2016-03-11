package com.niu.myapp.myapp.internal.di.components;

import android.content.Context;

import com.niu.myapp.myapp.internal.di.modules.ApplicationModule;
import com.niu.myapp.myapp.internal.di.modules.SubFriendsModule;
import com.niu.myapp.myapp.model.datasource.FriendDatastoreFactory;
import com.niu.myapp.myapp.view.Navigator;
import com.niu.myapp.myapp.view.activity.BaseActivity;
import com.niu.myapp.myapp.view.activity.MainActivity;
import com.niu.myapp.myapp.view.executor.NormalThreadExecutor;
import com.niu.myapp.myapp.view.executor.UIThreadExecutor;

import dagger.Component;
import javax.inject.Singleton;


/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);

    //Exposed to sub-graphs.
    Context context();

    Navigator getNavigator();

    UIThreadExecutor getUIThreadExecutor();
    NormalThreadExecutor getNormalThreadExecutor();
    //不能去掉否则出错，
    FriendDatastoreFactory getFriendDatastoreFactory();
    //subFriendComponent其实是所在类的一个内部类
//    SubFriendsComponent getSubFriendsComponent(SubFriendsModule module);
}
