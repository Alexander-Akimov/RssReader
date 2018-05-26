package com.akimov.rssreadermvp;

import android.app.Activity;
import android.app.Application;

import com.akimov.rssreadermvp.di.application.AppComponent;
import com.akimov.rssreadermvp.di.application.DaggerAppComponent;
import com.akimov.rssreadermvp.di.application.AppModule;

public class App extends Application {

    private AppComponent applicationComponent;

    public AppComponent getApplicationComponent() {
        return applicationComponent;
    }

    public static App get(Activity activity) {
        return (App) activity.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }
}
