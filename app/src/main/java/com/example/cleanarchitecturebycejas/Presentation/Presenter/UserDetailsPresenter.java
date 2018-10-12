package com.example.cleanarchitecturebycejas.Presentation.Presenter;

import com.android.annotations.NonNull;
import com.example.cleanarchitecturebycejas.Domain.Extension.DefaultErrorBundle;
import com.example.cleanarchitecturebycejas.Domain.Extension.ErrorBundle;
import com.example.cleanarchitecturebycejas.Domain.Interactor.GetUserDetails;
import com.example.cleanarchitecturebycejas.Domain.User;
import com.example.cleanarchitecturebycejas.Presentation.Extension.ErrorMessageFactory;
import com.example.cleanarchitecturebycejas.Presentation.Mapper.UserModelDataMapper;
import com.example.cleanarchitecturebycejas.Presentation.Model.UserModel;
import com.example.cleanarchitecturebycejas.Presentation.View.UserDetailsView;

import javax.inject.Inject;

import io.reactivex.observers.DefaultObserver;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class UserDetailsPresenter implements Presenter {

    private UserDetailsView viewDetailsView;

    private final GetUserDetails getUserDetailsUseCase;
    private final UserModelDataMapper userModelDataMapper;

    @Inject
    public UserDetailsPresenter(GetUserDetails getUserDetailsUseCase,
                                UserModelDataMapper userModelDataMapper) {
        this.getUserDetailsUseCase = getUserDetailsUseCase;
        this.userModelDataMapper = userModelDataMapper;
    }

    public void setView(@NonNull UserDetailsView view) {
        this.viewDetailsView = view;
    }

    @Override public void resume() {}

    @Override public void pause() {}

    @Override public void destroy() {
        this.getUserDetailsUseCase.dispose();
        this.viewDetailsView = null;
    }

    /**
     * Initializes the presenter by showing/hiding proper views
     * and retrieving user details.
     */
    public void initialize(int userId) {
        this.hideViewRetry();
        this.showViewLoading();
        this.getUserDetails(userId);
    }

    private void getUserDetails(int userId) {
        this.getUserDetailsUseCase.execute(new UserDetailsObserver(), GetUserDetails.Params.forUser(userId));
    }

    private void showViewLoading() {
        this.viewDetailsView.showLoading();
    }

    private void hideViewLoading() {
        this.viewDetailsView.hideLoading();
    }

    private void showViewRetry() {
        this.viewDetailsView.showRetry();
    }

    private void hideViewRetry() {
        this.viewDetailsView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.viewDetailsView.context(),
                errorBundle.getException());
        this.viewDetailsView.showError(errorMessage);
    }

    private void showUserDetailsInView(User user) {
        final UserModel userModel = this.userModelDataMapper.transform(user);
        this.viewDetailsView.renderUser(userModel);
    }

    private final class UserDetailsObserver extends DefaultObserver<User> {

        @Override public void onComplete() {
            UserDetailsPresenter.this.hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            UserDetailsPresenter.this.hideViewLoading();
            UserDetailsPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            UserDetailsPresenter.this.showViewRetry();
        }

        @Override public void onNext(User user) {
            UserDetailsPresenter.this.showUserDetailsInView(user);
        }
    }
}