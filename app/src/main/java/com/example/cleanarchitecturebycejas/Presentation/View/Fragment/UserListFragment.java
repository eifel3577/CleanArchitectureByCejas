package com.example.cleanarchitecturebycejas.Presentation.View.Fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.cleanarchitecturebycejas.Presentation.DI.Components.UserComponent;
import com.example.cleanarchitecturebycejas.Presentation.Model.UserModel;
import com.example.cleanarchitecturebycejas.Presentation.Presenter.UserListPresenter;
import com.example.cleanarchitecturebycejas.Presentation.View.Adapter.UsersAdapter;
import com.example.cleanarchitecturebycejas.Presentation.View.Adapter.UsersLayoutManager;
import com.example.cleanarchitecturebycejas.Presentation.View.UserListView;
import com.example.cleanarchitecturebycejas.R;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * фрагмент показывает список юзеров
 */
public class UserListFragment extends BaseFragment implements UserListView {

    /**
     * Interface for listening user list events.
     * Интерфейс для отслеживания кликает ли юзер на списке
     */
    public interface UserListListener {
        void onUserClicked(final UserModel userModel);
    }

    /**получает из даггера обьект UserListPresenter */
    @Inject
    UserListPresenter userListPresenter;
    /**получает из даггера обьект UsersAdapter */
    @Inject
    UsersAdapter usersAdapter;
    /**инициализирует вьюхи */
    @BindView(R.id.rv_users) RecyclerView rv_users;
    @BindView(R.id.rl_progress) RelativeLayout rl_progress;
    @BindView(R.id.rl_retry) RelativeLayout rl_retry;
    @BindView(R.id.bt_retry) Button bt_retry;

    /**обьект  */
    private UserListListener userListListener;

    public UserListFragment() {
        /**джанные будут сохранятся при поворотах */
        setRetainInstance(true);
    }

    /**инициализация использования единого лисенера с активити UserListActivity */
    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof UserListListener) {
            this.userListListener = (UserListListener) activity;
        }
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**получает компонент для даггера */
        this.getComponent(UserComponent.class).inject(this);
    }

    /**настройка биндинга, RecyclerView */
    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_user_list, container, false);
        ButterKnife.bind(this, fragmentView);
        setupRecyclerView();
        return fragmentView;
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /**передает экземпляр себя в презентер */
        this.userListPresenter.setView(this);

        if (savedInstanceState == null) {
            this.loadUserList();
        }
    }

    /**в презентере resume() не реализовано... */
    @Override public void onResume() {
        super.onResume();
        this.userListPresenter.resume();
    }

    /**в презентере onPause() не реализовано... */
    @Override public void onPause() {
        super.onPause();
        this.userListPresenter.pause();
    }

    /**обнуляем адаптер,отключаем биндинг */
    @Override public void onDestroyView() {
        super.onDestroyView();
        rv_users.setAdapter(null);
        ButterKnife.unbind(this);
    }

    /**дает команду презентеру отписаться от Disposable */
    @Override public void onDestroy() {
        super.onDestroy();
        this.userListPresenter.destroy();
    }

    /**обнуляет лисенер */
    @Override public void onDetach() {
        super.onDetach();
        this.userListListener = null;
    }

    /**пользователю начинает отображаться прогрессбар,ему задается бесконечное кручение*/
    @Override public void showLoading() {
        this.rl_progress.setVisibility(View.VISIBLE);
        this.getActivity().setProgressBarIndeterminateVisibility(true);
    }

    /**крутящийся прогрессбар скрывается */
    @Override public void hideLoading() {
        this.rl_progress.setVisibility(View.GONE);
        this.getActivity().setProgressBarIndeterminateVisibility(false);
    }

    /**показывается кнопка "Повторить" */
    @Override public void showRetry() {
        this.rl_retry.setVisibility(View.VISIBLE);
    }

    /**скрывается кнопка "Повториить" */
    @Override public void hideRetry() {
        this.rl_retry.setVisibility(View.GONE);
    }

    /**если коллекция не нулевая,отдает ее адаптеру для обработки */
    @Override public void renderUserList(Collection<UserModel> userModelCollection) {
        if (userModelCollection != null) {
            this.usersAdapter.setUsersCollection(userModelCollection);
        }
    }

    /**получает обьект UserModel,проверяет активный ли лисенер userListListener,если да отдает
     * ему UserModel,на котором кликнули  */
    @Override public void viewUser(UserModel userModel) {
        if (this.userListListener != null) {
            this.userListListener.onUserClicked(userModel);
        }
    }

    /**показывает ошибку */
    @Override public void showError(String message) {
        this.showToastMessage(message);
    }

    /**возвращает контекст приложения,получая его из активити,к которой прикреплен этот фрагмент */
    @Override public Context context() {
        return this.getActivity().getApplicationContext();
    }

    /**настройка RecyclerView */
    private void setupRecyclerView() {
        this.usersAdapter.setOnItemClickListener(onItemClickListener);
        this.rv_users.setLayoutManager(new UsersLayoutManager(context()));
        this.rv_users.setAdapter(usersAdapter);
    }

    /**
     * Загрузка юзеров,дает команду презентеру запускать загрузку
     */
    private void loadUserList() {
        this.userListPresenter.initialize();
    }

    /**нажатие на кнопку "Повторить" , повторно дает презентеру команду грузить лист*/
    @OnClick(R.id.bt_retry)
    void onButtonRetryClick() {
        UserListFragment.this.loadUserList();
    }

    /**нажатие на юзера в списке,отдает нажатого юзера презентеру */
    private UsersAdapter.OnItemClickListener onItemClickListener =
            new UsersAdapter.OnItemClickListener() {
                @Override public void onUserItemClicked(UserModel userModel) {
                    if (UserListFragment.this.userListPresenter != null && userModel != null) {
                        UserListFragment.this.userListPresenter.onUserClicked(userModel);
                    }
                }
            };
}