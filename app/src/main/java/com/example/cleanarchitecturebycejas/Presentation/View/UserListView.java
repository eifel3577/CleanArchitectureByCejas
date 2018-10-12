package com.example.cleanarchitecturebycejas.Presentation.View;

import com.example.cleanarchitecturebycejas.Presentation.Model.UserModel;

import java.util.Collection;

/**
 * Интерфейс представляющий View в модели MVP.Этот интерфейс реализуют все View работающие с UserModel
 */
public interface UserListView extends LoadDataView {
    /**
     * отображение списка юзеров пользователю
     *
     * @param userModelCollection коллекция{@link UserModel} котораябудет отображаться
     */
    void renderUserList(Collection<UserModel> userModelCollection);

    /**Просмотр детальной информации о {@link UserModel}
     *
     * @param userModel юзер детальная информация о котором должна отображаться
     */
    void viewUser(UserModel userModel);
}