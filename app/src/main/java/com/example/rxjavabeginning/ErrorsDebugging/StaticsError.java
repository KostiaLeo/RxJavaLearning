package com.example.rxjavabeginning.ErrorsDebugging;

import rx.Observable;
import rx.Observer;
import rx.functions.Func1;

class StaticsError {
    static Observable<String> stringObservableData = Observable.just("1", "2", "L", "4", "5");
    static Observable<Long> observable = StaticsError.stringObservableData.map(new Func1<String, Long>() {
        @Override
        public Long call(String s) {
            return Long.parseLong(s);
        }
    });

    static void subscribe(Observable<Long> observable){
        observable.subscribe(new Observer<Long>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError: " + e);
            }

            @Override
            public void onNext(Long aLong) {
                System.out.println("onNext(): " + aLong);
            }
        });
    }
}
