package com.example.cleanarchitecturebycejas.Presentation.DI.Components;

import android.content.Context;

import com.example.cleanarchitecturebycejas.Domain.Executor.PostExecutionThread;
import com.example.cleanarchitecturebycejas.Domain.Executor.ThreadExecutor;
import com.example.cleanarchitecturebycejas.Domain.Repository.UserRepository;
import com.example.cleanarchitecturebycejas.Presentation.DI.Modules.ApplicationModule;
import com.example.cleanarchitecturebycejas.Presentation.View.Activity.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);

    //Exposed to sub-graphs.
    Context context();
    ThreadExecutor threadExecutor();
    PostExecutionThread postExecutionThread();
    UserRepository userRepository();
}