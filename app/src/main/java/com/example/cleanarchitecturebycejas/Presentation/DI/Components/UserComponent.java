package com.example.cleanarchitecturebycejas.Presentation.DI.Components;

import com.example.cleanarchitecturebycejas.Presentation.DI.Modules.ActivityModule;
import com.example.cleanarchitecturebycejas.Presentation.DI.Modules.UserModule;
import com.example.cleanarchitecturebycejas.Presentation.View.Fragment.UserDetailsFragment;
import com.example.cleanarchitecturebycejas.Presentation.View.Fragment.UserListFragment;

import dagger.Component;

/**
 * A scope {@link com.fernandocejas.android10.sample.presentation.internal.di.PerActivity} component.
 * Injects user specific Fragments.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, UserModule.class})
public interface UserComponent extends ActivityComponent {
    void inject(UserListFragment userListFragment);
    void inject(UserDetailsFragment userDetailsFragment);
}