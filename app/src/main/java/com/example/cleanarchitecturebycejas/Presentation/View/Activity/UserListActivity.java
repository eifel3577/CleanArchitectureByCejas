package com.example.cleanarchitecturebycejas.Presentation.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.example.cleanarchitecturebycejas.Presentation.DI.Components.UserComponent;
import com.example.cleanarchitecturebycejas.Presentation.DI.HasComponent;
import com.example.cleanarchitecturebycejas.Presentation.Model.UserModel;
import com.example.cleanarchitecturebycejas.Presentation.View.Fragment.UserListFragment;
import com.example.cleanarchitecturebycejas.R;

/**
 * активити которое показывает список юзеров
 */
public class UserListActivity extends BaseActivity implements HasComponent<UserComponent>,
        UserListFragment.UserListListener {

    /**возвращает интент для открытия самого себя.Это такой подход чтобы передавать данные между активити */
    public static Intent getCallingIntent(Context context) {
        return new Intent(context, UserListActivity.class);
    }

    private UserComponent userComponent;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**в правом верхнем углу экрана будет вращаться прогрессбар */
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_layout);
        /**по умолчанию сразу отображается UserListFragment*/
        this.initializeInjector();
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, new UserListFragment());
        }
    }

    /**активируется UserComponent для доступа к даггеру */
    private void initializeInjector() {
        this.userComponent = DaggerUserComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override public UserComponent getComponent() {
        return userComponent;
    }

    @Override public void onUserClicked(UserModel userModel) {
        this.navigator.navigateToUserDetails(this, userModel.getUserId());
    }
}