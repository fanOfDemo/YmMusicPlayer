package com.yw.musicplayer.domain.interactor;


import com.yw.musicplayer.domain.executor.PostExecutionThread;
import com.yw.musicplayer.domain.executor.ThreadExecutor;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This interface represents a execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 * <p>
 * By convention each UseCase implementation will return the result using a {@link Subscriber}
 * that will execute its job in a background thread and will post the result in the UI thread.
 */
public abstract class UseCase<T> {

    protected final ThreadExecutor threadExecutor;
    protected final PostExecutionThread postExecutionThread;

    private CompositeDisposable subscription = new CompositeDisposable();

    protected UseCase(ThreadExecutor threadExecutor,
                      PostExecutionThread postExecutionThread) {
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    /**
     * Builds an {@link Observable} which will be used when executing the current {@link UseCase}.
     */
    protected abstract Observable<T> buildUseCaseObservable();

    /**
     * Executes the current use case.
     *
     * @param UseCaseSubscriber The guy who will be listen to the observable build
     *                          with {@link #buildUseCaseObservable()}.
     */
    public void execute(Subscriber<T> UseCaseSubscriber) {
        buildUseCaseObservable()
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler())
                .subscribe((Observer<? super T>) UseCaseSubscriber);
    }

    /**
     * Executes the current use case.
     *
     * @param useCaseObserver The guy who will be listen to the observable build
     *                        with {@link #buildUseCaseObservable()}.
     */
    public void execute(final Observer<T> useCaseObserver) {
        buildUseCaseObservable()
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler())
                .subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        subscription.add(d);
                    }

                    @Override
                    public void onNext(@NonNull T t) {
                        useCaseObserver.onNext(t);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        useCaseObserver.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        useCaseObserver.onComplete();
                    }
                });
    }

    /**
     * Un-subscribes from current {@link Subscription}.
     */
    public void unsubscribe() {
        subscription.clear();
    }
}
