package com.example.cleanarchitecturebycejas.Presentation.DI.Modules;


import android.content.Context;

import com.example.cleanarchitecturebycejas.Data.Cashe.UserCache;
import com.example.cleanarchitecturebycejas.Data.Cashe.UserCacheImpl;
import com.example.cleanarchitecturebycejas.Data.Executor.JobExecutor;
import com.example.cleanarchitecturebycejas.Data.Repository.UserDataRepository;
import com.example.cleanarchitecturebycejas.Domain.Executor.PostExecutionThread;
import com.example.cleanarchitecturebycejas.Domain.Executor.ThreadExecutor;
import com.example.cleanarchitecturebycejas.Domain.Repository.UserRepository;
import com.example.cleanarchitecturebycejas.Presentation.AndroidApplication;
import com.example.cleanarchitecturebycejas.Presentation.UIThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
    private final AndroidApplication application;

    public ApplicationModule(AndroidApplication application) {
        this.application = application;
    }

    @Provides @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides @Singleton
    UserCache provideUserCache(UserCacheImpl userCache) {
        return userCache;
    }

    @Provides
    @Singleton
    UserRepository provideUserRepository(UserDataRepository userDataRepository) {
        return userDataRepository;
    }
}