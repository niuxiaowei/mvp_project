package com.niu.myapp.myapp.internal.di.modules;

import android.content.Context;

import com.niu.myapp.myapp.BaseApplication;
import com.niu.myapp.myapp.base.di.modules.BaseApplicationModule;
import com.niu.myapp.myapp.common.executor.NormalThreadExecutor;
import com.niu.myapp.myapp.common.executor.UIThreadExecutor;
import com.niu.myapp.myapp.data.datastore.FriendDatastoreFactory;
import com.niu.myapp.myapp.data.localdata.db.user.UserDatabase;
import com.niu.myapp.myapp.view.Navigator;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule  {
    private final BaseApplication application;

    public ApplicationModule(BaseApplication application) {
        this.application = application;
    }


    @Provides @Singleton Navigator provideNavigator() {
        return new Navigator();
    }



    @Provides @Singleton
    public FriendDatastoreFactory provideFriendDatastoreFactory(UserDatabase userDatabase){
        return new FriendDatastoreFactory(userDatabase);
    }

    @Provides @Singleton
    UserDatabase provideUserDatabase(){
        return new UserDatabase(this.application);
    }
}
