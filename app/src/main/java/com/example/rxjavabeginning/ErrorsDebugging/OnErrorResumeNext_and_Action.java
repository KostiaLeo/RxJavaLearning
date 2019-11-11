package com.example.rxjavabeginning.ErrorsDebugging;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class OnErrorResumeNext_and_Action {
    public static void main(String[] args) {
        OnErrorResumeNext_and_Action o = new OnErrorResumeNext_and_Action();
        o.onErrorResumeNext_errorDefining_withAction();
    }

// onErrorResumeNext method allows you to set own Observable data emitting after Error event
// Starting data stops emitting after error
// and Observable inside onErrorResumeNext parameter is beginning work
    private void simpleOnErrorResumeNext() {
        Observable<Long> observable = StaticsError.stringObservableData.map(new Func1<String, Long>() {
            @Override
            public Long call(String s) {
                return Long.parseLong(s);
            }
        }).onErrorResumeNext(Observable.just(10L, 11L, 12L));

        StaticsError.subscribe(observable);
    }

// Actually the same as though previous example but this defines work with throwable
    private void onErrorResumeNext_errorDefining() {
        Observable<Long> observable = StaticsError.stringObservableData
                .map(new Func1<String, Long>() {
                    @Override
                    public Long call(String s) {
                        return Long.parseLong(s);
                    }
                }).onErrorResumeNext(new Func1<Throwable, Observable<? extends Long>>() {
                    @Override
                    public Observable<? extends Long> call(Throwable throwable) {
                        System.out.println("OnErrorResumeNext: " + throwable);
                        return Observable.just(10L, 11L, 12L);
                    }
                });

        StaticsError.subscribe(observable);
    }

// As we've called onErrorResumeNext which deal with error
// so we don't need creation full observable
// and Action<T> usage is enough
    private void onErrorResumeNext_errorDefining_withAction() {
        Observable<Long> observable = StaticsError.stringObservableData
                .map(new Func1<String, Long>() {
                    @Override
                    public Long call(String s) {
                        return Long.parseLong(s);
                    }
                }).onErrorResumeNext(Observable.just(10L, 11L, 12L));
        Action1<Long> action = new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                System.out.println("Action call onNext:" + aLong);
            }
        };
        observable.subscribe(action);
    }
}