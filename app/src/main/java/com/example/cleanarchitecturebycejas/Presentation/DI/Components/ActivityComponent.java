package com.example.cleanarchitecturebycejas.Presentation.DI.Components;

import android.app.Activity;

import com.example.cleanarchitecturebycejas.Presentation.DI.Modules.ActivityModule;

import dagger.Component;

/**
 * A base component upon which fragment's components may depend.
 * Activity-level components should extend this component.
 *
 * Subtypes of ActivityComponent should be decorated with annotation:
 * {@link com.fernandocejas.android10.sample.presentation.internal.di.PerActivity}
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
interface ActivityComponent {
    //Exposed to sub-graphs.
    Activity activity();
}