package com.example.cleanarchitecturebycejas.Presentation.View.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.example.cleanarchitecturebycejas.Presentation.AndroidApplication;
import com.example.cleanarchitecturebycejas.Presentation.DI.Components.ApplicationComponent;
import com.example.cleanarchitecturebycejas.Presentation.DI.Modules.ActivityModule;
import com.example.cleanarchitecturebycejas.Presentation.Navigation.Navigator;

import javax.inject.Inject;

public abstract class BaseActivity extends Activity {

    /**получает объект класса Navigator через Dagger */
    @Inject
    Navigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getApplicationComponent().inject(this);
    }

    /**Добавляет фрагмент в макет этого активити
     *
     *
     * @param containerViewId контейнер куда добавляется фрагмент
     * @param fragment фрагмент который должен быть добавлен
     */
    protected void addFragment(int containerViewId, Fragment fragment) {
        final FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.commit();
    }

    /**Дает Main Application component для внедрения зависимости
     *@return возвращает ApplicationComponent
     */
    protected ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication) getApplication()).getApplicationComponent();
    }

    /**
     * Дает Activity module для внедрения зависимости
     *@return возвращает  ActivityModule
     */
    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
}
