package com.niu.myapp.myapp.internal.di.components;

import android.content.Context;

import com.niu.myapp.myapp.internal.di.modules.ApplicationModule;
import com.niu.myapp.myapp.view.Navigator;
import com.niu.myapp.myapp.view.activity.BaseActivity;
import com.niu.myapp.myapp.view.activity.MainActivity;

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
}
