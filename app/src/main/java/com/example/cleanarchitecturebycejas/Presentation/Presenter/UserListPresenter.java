package com.example.cleanarchitecturebycejas.Presentation.Presenter;

import com.android.annotations.NonNull;
import com.example.cleanarchitecturebycejas.Domain.Extension.DefaultErrorBundle;
import com.example.cleanarchitecturebycejas.Domain.Extension.ErrorBundle;
import com.example.cleanarchitecturebycejas.Domain.Interactor.DefaultObserver;
import com.example.cleanarchitecturebycejas.Domain.Interactor.GetUserList;
import com.example.cleanarchitecturebycejas.Domain.User;
import com.example.cleanarchitecturebycejas.Presentation.DI.PerActivity;
import com.example.cleanarchitecturebycejas.Presentation.Extension.ErrorMessageFactory;
import com.example.cleanarchitecturebycejas.Presentation.Mapper.UserModelDataMapper;
import com.example.cleanarchitecturebycejas.Presentation.Model.UserModel;
import com.example.cleanarchitecturebycejas.Presentation.View.UserListView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * Презентер {@link Presenter} осуществляет коммуникацию между View и Model в слое Presentation
 */
@PerActivity
public class UserListPresenter implements Presenter {

    private UserListView viewListView;

    /**обьект GetUserList.Он находится в другом слое,в Domain */
    private final GetUserList getUserListUseCase;
    /**обьект GetUserList.Он находится в другом слое,в Domain */
    private final UserModelDataMapper userModelDataMapper;

    /**презентер будет предоставляться как зависимость в другие классы */
    @Inject
    public UserListPresenter(GetUserList getUserListUserCase,
                             UserModelDataMapper userModelDataMapper) {
        this.getUserListUseCase = getUserListUserCase;
        this.userModelDataMapper = userModelDataMapper;
    }

    /**принимает View, с которым взаимодействует */
    public void setView(@NonNull UserListView view) {
        this.viewListView = view;
    }

    @Override public void resume() {}

    @Override public void pause() {}

    /**отписывается от Disposable, обнуляет ссылку на прикрепленное вью (в данном случае UserListFragment) */
    @Override public void destroy() {
        this.getUserListUseCase.dispose();
        this.viewListView = null;
    }

    /**
     * запускает загрузку списка пользователей
     */
    public void initialize() {
        this.loadUserList();
    }

    /**
     * загружает юзеров
     */
    private void loadUserList() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getUserList();
    }

    /**получает юзера на котором кликнули */
    public void onUserClicked(UserModel userModel) {
        this.viewListView.viewUser(userModel);
    }

    /**дает View команду показывать View с прогрессбаром индикатором загрузки */
    private void showViewLoading() {
        this.viewListView.showLoading();
    }

    /**дает View команду скрыть View с прогрессбаром индикатором загрузки*/
    private void hideViewLoading() {
        this.viewListView.hideLoading();
    }

    /**дает View команду показать View "Повторить" */
    private void showViewRetry() {
        this.viewListView.showRetry();
    }

    /**дает View команду скрыть View "Повторить" */
    private void hideViewRetry() {
        this.viewListView.hideRetry();
    }

    /**принимает на вход кастомное исключение,с помощью фабрики конвертирует его
     * в удобочитаемое сообщение,дает команду View показать это сообщение
     * пользователю*/
    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.viewListView.context(),
                errorBundle.getException());
        this.viewListView.showError(errorMessage);
    }

    /**получает на вход коллекцию User-ов,трансформирует ее в коллекцию
     * UserModel-ей,дает команду View отобразить список UserModel-ей пользователю */
    private void showUsersCollectionInView(Collection<User> usersCollection) {
        final Collection<UserModel> userModelsCollection =
                this.userModelDataMapper.transform(usersCollection);
        this.viewListView.renderUserList(userModelsCollection);
    }

    /**вызывает метод обьекта слоя Domain GetUserList execute,передает ему
     *  в параметр наблюдатель UserListObserver*/
    private void getUserList() {
        this.getUserListUseCase.execute(new UserListObserver(), null);
    }

    /**наблюдатель наследует интерфейс DefaultObserver */
    private final class UserListObserver extends DefaultObserver<List<User>> {

        /**источник успешно закончил трансляцию*/
        @Override public void onComplete() {
            UserListPresenter.this.hideViewLoading();
        }

        /**ошибка получения данных от источника */
        @Override public void onError(Throwable e) {
            UserListPresenter.this.hideViewLoading();
            UserListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            UserListPresenter.this.showViewRetry();
        }

        /**источник прислал данные (список юзеров) */
        @Override public void onNext(List<User> users) {
            UserListPresenter.this.showUsersCollectionInView(users);
        }
    }
}