package com.example.cleanarchitecturebycejas.Presentation.DI.Modules;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;

/**
 * модуль,возвращающий оболочку активити
 *
 */
@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    /**
     * Предоставляет activity для зависимостей,которые в нем нуждаются
     */
    @Provides
    @PerActivity
    Activity activity() {
        return this.activity;
    }
}