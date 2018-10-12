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
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class UserListPresenter implements Presenter {

    private UserListView viewListView;

    /**обьект GetUserList.Он находится в другом слое,в Domain */
    private final GetUserList getUserListUseCase;
    private final UserModelDataMapper userModelDataMapper;

    @Inject
    public UserListPresenter(GetUserList getUserListUserCase,
                             UserModelDataMapper userModelDataMapper) {
        this.getUserListUseCase = getUserListUserCase;
        this.userModelDataMapper = userModelDataMapper;
    }

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

    private void hideViewLoading() {
        this.viewListView.hideLoading();
    }

    private void showViewRetry() {
        this.viewListView.showRetry();
    }

    /**дает View команду скрыть View "Повторить" */
    private void hideViewRetry() {
        this.viewListView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.viewListView.context(),
                errorBundle.getException());
        this.viewListView.showError(errorMessage);
    }

    private void showUsersCollectionInView(Collection<User> usersCollection) {
        final Collection<UserModel> userModelsCollection =
                this.userModelDataMapper.transform(usersCollection);
        this.viewListView.renderUserList(userModelsCollection);
    }

    /**вызывает метод обьекта слоя Domain GetUserList execute,передает ему в параметр наблюдатель UserListObserver*/
    private void getUserList() {
        this.getUserListUseCase.execute(new UserListObserver(), null);
    }

    /**наблюдатель */
    private final class UserListObserver extends DefaultObserver<List<User>> {

        @Override public void onComplete() {
            UserListPresenter.this.hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            UserListPresenter.this.hideViewLoading();
            UserListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            UserListPresenter.this.showViewRetry();
        }

        @Override public void onNext(List<User> users) {
            UserListPresenter.this.showUsersCollectionInView(users);
        }
    }
}