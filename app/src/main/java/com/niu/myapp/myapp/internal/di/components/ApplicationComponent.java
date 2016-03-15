package com.niu.myapp.myapp.internal.di.components;

import android.content.Context;

import com.niu.myapp.myapp.common.executor.NormalThreadExecutor;
import com.niu.myapp.myapp.common.executor.UIThreadExecutor;
import com.niu.myapp.myapp.data.localdata.db.user.UserDatabase;
import com.niu.myapp.myapp.internal.di.modules.ApplicationModule;
import com.niu.myapp.myapp.data.datastore.FriendDatastoreFactory;
import com.niu.myapp.myapp.view.Navigator;
import com.niu.myapp.myapp.view.activity.BaseActivity;


import dagger.Component;
import javax.inject.Singleton;


/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);

    //以下暴漏的方法主要是给子component爆漏的
    Context context();

    Navigator getNavigator();

    UIThreadExecutor getUIThreadExecutor();
    NormalThreadExecutor getNormalThreadExecutor();
    //不能去掉否则出错，
    FriendDatastoreFactory getFriendDatastoreFactory();
    UserDatabase getUserDatabase();

    //subFriendComponent其实是所在类的一个内部类
//    SubFriendsComponent getSubFriendsComponent(SubFriendsModule module);
}
