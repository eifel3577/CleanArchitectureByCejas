package com.example.cleanarchitecturebycejas.Presentation.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.example.cleanarchitecturebycejas.Presentation.DI.Components.UserComponent;
import com.example.cleanarchitecturebycejas.Presentation.DI.HasComponent;
import com.example.cleanarchitecturebycejas.Presentation.View.Fragment.UserDetailsFragment;
import com.example.cleanarchitecturebycejas.R;

/**
 * активити для показа детальной информации о выбранном пользователе
 */
public class UserDetailsActivity extends BaseActivity implements HasComponent<UserComponent> {

    private static final String INTENT_EXTRA_PARAM_USER_ID = "org.android10.INTENT_PARAM_USER_ID";
    private static final String INSTANCE_STATE_PARAM_USER_ID = "org.android10.STATE_PARAM_USER_ID";

    /**метод для получения в интенте ID пользователя */
    public static Intent getCallingIntent(Context context, int userId) {
        Intent callingIntent = new Intent(context, UserDetailsActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_USER_ID, userId);
        return callingIntent;
    }

    private int userId;
    private UserComponent userComponent;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**включает расширенные функции экрана.В данном случае включает неопределенный прогресс */
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_layout);

        this.initializeActivity(savedInstanceState);
        this.initializeInjector();
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putInt(INSTANCE_STATE_PARAM_USER_ID, this.userId);
        }
        super.onSaveInstanceState(outState);
    }

    /**
     * метод достает ID из интента, вызывает метод родителя (BaseActivity) addFragment,в нем добавляет в контейнер
     * фрагмент UserDetailsFragment по конкретному юзеру,используя его ID
     */
    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            this.userId = getIntent().getIntExtra(INTENT_EXTRA_PARAM_USER_ID, -1);
            addFragment(R.id.fragmentContainer, UserDetailsFragment.forUser(userId));
        } else {
            /**если активити пересоздалось,например при повороте экрана,то достает сохраненный перед поворотом экрана
             * ID из bundle */
            this.userId = savedInstanceState.getInt(INSTANCE_STATE_PARAM_USER_ID);
        }
    }

    private void initializeInjector() {
        this.userComponent = DaggerUserComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override public UserComponent getComponent() {
        return userComponent;
    }
}