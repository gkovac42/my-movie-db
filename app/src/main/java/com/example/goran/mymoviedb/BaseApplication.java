package com.example.goran.mymoviedb;

import android.app.Application;

import com.example.goran.mymoviedb.di.AppComponent;
import com.example.goran.mymoviedb.di.AppModule;
import com.example.goran.mymoviedb.di.DaggerAppComponent;
import com.example.goran.mymoviedb.data.remote.RemoteModule;

/**
 * Created by Goran on 28.12.2017..
 */

public class BaseApplication extends Application {

    private AppComponent appComponent;

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .remoteModule(new RemoteModule())
                .build();

        appComponent.inject(this);
    }
}
