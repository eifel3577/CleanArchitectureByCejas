package com.example.cleanarchitecturebycejas.Presentation;


import android.app.Application;

import com.example.cleanarchitecturebycejas.BuildConfig;
import com.example.cleanarchitecturebycejas.Presentation.DI.Components.ApplicationComponent;
import com.example.cleanarchitecturebycejas.Presentation.DI.Modules.ApplicationModule;
import com.squareup.leakcanary.LeakCanary;

public class AndroidApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override public void onCreate() {
        super.onCreate();
        this.initializeInjector();
        this.initializeLeakDetection();
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

    /**установка LeakCanary для мониторинга memory leaks */
    private void initializeLeakDetection() {
        if (BuildConfig.DEBUG) {
            LeakCanary.install(this);
        }
    }
}