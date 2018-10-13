package com.example.cleanarchitecturebycejas.Presentation.Mapper;

import com.example.cleanarchitecturebycejas.Domain.User;
import com.example.cleanarchitecturebycejas.Presentation.DI.PerActivity;
import com.example.cleanarchitecturebycejas.Presentation.Model.UserModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

/**
 * Маппер используется для трансформации User (из Domain слоя) в UserModel из Presentation слоя
 */

/**метка указывает на скоуп уровня активити */
@PerActivity
public class UserModelDataMapper {

    /**класс предоставляется другим классам как зависимость */
    @Inject
    public UserModelDataMapper() {}

    /**
     * Трансформирует {@link User} в {@link UserModel}.
     * @param user обьект типа User
     * @return {@link UserModel}. обьект типа UserModel
     */
    public UserModel transform(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final UserModel userModel = new UserModel(user.getUserId());
        userModel.setCoverUrl(user.getCoverUrl());
        userModel.setFullName(user.getFullName());
        userModel.setEmail(user.getEmail());
        userModel.setDescription(user.getDescription());
        userModel.setFollowers(user.getFollowers());

        return userModel;
    }

    /**
     * Трансформирует коллекцию User-ов {@link User}  в коллекцию
     * UserModel-ей {@link UserModel}
     * @param usersCollection коллекция User-ов
     * @return List of {@link UserModel}. коллекция UserModel-ей
     */
    public Collection<UserModel> transform(Collection<User> usersCollection) {
        Collection<UserModel> userModelsCollection;

        if (usersCollection != null && !usersCollection.isEmpty()) {
            userModelsCollection = new ArrayList<>();
            for (User user : usersCollection) {
                userModelsCollection.add(transform(user));
            }
        } else {
            userModelsCollection = Collections.emptyList();
        }

        return userModelsCollection;
    }
}