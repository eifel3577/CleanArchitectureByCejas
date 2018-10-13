package com.example.cleanarchitecturebycejas.Domain.Interactor;

import com.example.cleanarchitecturebycejas.Domain.Executor.PostExecutionThread;
import com.example.cleanarchitecturebycejas.Domain.Executor.ThreadExecutor;
import com.example.cleanarchitecturebycejas.Domain.Repository.UserRepository;
import com.example.cleanarchitecturebycejas.Domain.User;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving a collection of all {@link User}.
 *
 * Этот класс является имплементацией {@link UseCase} .Данная имплементация это Use Case для
 * получения коллекции {@link User}.
 */
public class GetUserList extends UseCase<List<User>, Void> {

    /**обьект репозитория */
    private final UserRepository userRepository;

    /**класс будет передаваться как зависимость в другие классы */
    @Inject
    GetUserList(UserRepository userRepository, ThreadExecutor threadExecutor,
                PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    /**реализация метода родителя.Создает Observable */
    @Override
    Observable<List<User>> buildUseCaseObservable(Void unused) {
        return this.userRepository.users();
    }
}