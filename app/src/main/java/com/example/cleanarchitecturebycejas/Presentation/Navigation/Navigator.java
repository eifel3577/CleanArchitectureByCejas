package com.example.cleanarchitecturebycejas.Presentation.Navigation;

import android.content.Context;
import android.content.Intent;

import com.example.cleanarchitecturebycejas.Presentation.View.Activity.UserDetailsActivity;
import com.example.cleanarchitecturebycejas.Presentation.View.Activity.UserListActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 *
 * Класс обеспечивающий навигацию в приложении
 */
@Singleton
public class Navigator {

    /**класс будет передаваться как зависимость в другие классы */
    @Inject
    public Navigator() {
        //empty
    }

    /**
     * направляет пользователя на показ списка юзеров
     *
     * @param context контекст нужный для открытия соответствующего активити
     */
    public void navigateToUserList(Context context) {
        if (context != null) {
            Intent intentToLaunch = UserListActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    /**
     * направляет пользователя на показ детельной инфо о юзере
     *
     * @param context контекст нужный для открытия соответствующего активити
     */
    public void navigateToUserDetails(Context context, int userId) {
        if (context != null) {
            Intent intentToLaunch = UserDetailsActivity.getCallingIntent(context, userId);
            context.startActivity(intentToLaunch);
        }
    }
}