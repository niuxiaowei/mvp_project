package com.niu.myapp.myapp.internal.di.components;

import android.content.Context;

import com.niu.myapp.myapp.base.di.components.BaseApplicationComponent;
import com.niu.myapp.myapp.base.di.modules.BaseApplicationModule;
import com.niu.myapp.myapp.common.executor.NormalThreadExecutor;
import com.niu.myapp.myapp.common.executor.UIThreadExecutor;
import com.niu.myapp.myapp.data.localdata.db.user.UserDatabase;
import com.niu.myapp.myapp.internal.di.modules.ApplicationModule;
import com.niu.myapp.myapp.data.datastore.FriendDatastoreFactory;
import com.niu.myapp.myapp.view.Navigator;


import dagger.Component;
import javax.inject.Singleton;


/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = {ApplicationModule.class, BaseApplicationModule.class})
public interface ApplicationComponent extends BaseApplicationComponent{



    Navigator getNavigator();


    //不能去掉否则出错，
    FriendDatastoreFactory getFriendDatastoreFactory();
    UserDatabase getUserDatabase();

    //subFriendComponent其实是所在类的一个内部类
//    SubFriendsComponent getSubFriendsComponent(SubFriendsModule module);
}
