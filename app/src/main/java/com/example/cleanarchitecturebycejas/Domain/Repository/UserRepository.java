package com.example.cleanarchitecturebycejas.Domain.Repository;

import com.example.cleanarchitecturebycejas.Domain.User;

import java.util.List;

import io.reactivex.Observable;

/**
 * Интерфейс который представляет собой Репозиторий для получения {@link User}
 */
public interface UserRepository {
    /**
     * Возвращает {@link Observable} который транслирует список {@link User}.
     */
    Observable<List<User>> users();

    /**
     * Возвращает {@link Observable} который будет транслировать конкретного {@link User}.
     * @param userId ID юзера который будет транслироваться
     */
    Observable<User> user(final int userId);
}