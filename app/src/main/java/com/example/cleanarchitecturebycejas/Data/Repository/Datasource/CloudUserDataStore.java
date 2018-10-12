package com.example.cleanarchitecturebycejas.Data.Repository.Datasource;

import com.example.cleanarchitecturebycejas.Data.Cashe.UserCache;
import com.example.cleanarchitecturebycejas.Data.Entity.UserEntity;
import com.example.cleanarchitecturebycejas.Data.Net.RestApi;

import java.util.List;

import io.reactivex.Observable;

/**
 * {@link UserDataStore} implementation based on connections to the api (Cloud).
 */
class CloudUserDataStore implements UserDataStore {

    private final RestApi restApi;
    private final UserCache userCache;

    /**
     * Construct a {@link UserDataStore} based on connections to the api (Cloud).
     *
     * @param restApi The {@link RestApi} implementation to use.
     * @param userCache A {@link UserCache} to cache data retrieved from the api.
     */
    CloudUserDataStore(RestApi restApi, UserCache userCache) {
        this.restApi = restApi;
        this.userCache = userCache;
    }

    @Override public Observable<List<UserEntity>> userEntityList() {
        return this.restApi.userEntityList();
    }

    @Override public Observable<UserEntity> userEntityDetails(final int userId) {
        return this.restApi.userEntityById(userId).doOnNext(CloudUserDataStore.this.userCache::put);
    }
}