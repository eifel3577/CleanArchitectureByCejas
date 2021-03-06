package com.example.cleanarchitecturebycejas.Domain.Interactor;

import com.example.cleanarchitecturebycejas.Domain.Executor.PostExecutionThread;
import com.example.cleanarchitecturebycejas.Domain.Executor.ThreadExecutor;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 *
 * This interface represents a execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 * Абстрактный класс - интерактор для Use Case ( Use Case - "Сценарий использования", "вариант использования",
 * "прецедент использования" (англ. use case) — в разработке программного обеспечения это описание
 * поведения системы, когда она взаимодействует с кем-то (или чем-то) из внешней среды) Этот интерфейс представляет
 * блок исполнения для разных вариантов Use Case
 * Каждая имплементация Use Case должна возвращать результат используя {@link DisposableObserver},
 * который будет выполнятьсвою работу в фоновом потоке и возвращать результат в UI поток
 *
 */
public abstract class UseCase<T, Params> {

    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;
    /**CompositeDisposable это одиночный контейнер,который может хранить в себе несколько других disposable*/
    private final CompositeDisposable disposables;

    UseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
        this.disposables = new CompositeDisposable();
    }

    /**
     * создает {@link Observable} который будет использоваться когда выполняется текущий
     * {@link UseCase}
     */
    abstract Observable<T> buildUseCaseObservable(Params params);

    /**
     * Выполняет текущую пользовательскую задачу
     *
     * @param observer {@link DisposableObserver} наблюдатель,который будет слушать трансляцию
     * Observable, созданного методом {@link #buildUseCaseObservable(Params)} ()} .
     * @param params Parameters (Optional) используется для построения\выполнения use case.
     */
    public void execute(DisposableObserver<T> observer, Params params) {
        Preconditions.checkNotNull(observer);
        final Observable<T> observable = this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());
        addDisposable(observable.subscribeWith(observer));
    }

    /**
     * Dispose from current {@link CompositeDisposable}.
     */
    public void dispose() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }

    /**
     * Dispose from current {@link CompositeDisposable}.
     *
     */
    private void addDisposable(Disposable disposable) {
        Preconditions.checkNotNull(disposable);
        Preconditions.checkNotNull(disposables);
        disposables.add(disposable);
    }
}