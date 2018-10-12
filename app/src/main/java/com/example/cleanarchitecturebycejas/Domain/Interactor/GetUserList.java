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
 */
public class GetUserList extends UseCase<List<User>, Void> {

    private final UserRepository userRepository;

    @Inject
    GetUserList(UserRepository userRepository, ThreadExecutor threadExecutor,
                PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override
    Observable<List<User>> buildUseCaseObservable(Void unused) {
        return this.userRepository.users();
    }
}