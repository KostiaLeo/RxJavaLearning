package com.example.rxjavabeginning.UnionOperations;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

public class Zip_combineLatest_withLatestFrom {
    // simple zip has been already specified in ErrorsDebugging - retryWhen - expandedByErrorShowRetryWhen
    Observer<Long> observer = new Observer<Long>() {
        @Override
        public void onCompleted() {
            System.out.println("onCompleted");
        }
        @Override
        public void onError(Throwable e) {}
        @Override
        public void onNext(Long aLong) {
            System.out.println("onNext " + aLong);
        }
    };

    Observable<String> simpleStringObservble = Observable.just("Гундёж", "Катакомбы", "Бошки Дымятся", "Sayonara Boy", "Sayonara Boy ろ", "Sayonara Boy X", "Sayonara Boy 143");

    Observable<Long> observable2 = Observable.interval(1000, TimeUnit.MILLISECONDS);
    public void zipWithManualInterval(){
        Observable.zip(simpleStringObservble, observable2, new Func2<String, Long, Object>() {
            @Override
            public Object call(String s, Long aLong) {
                return s;
            }
        }).subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                System.out.println("onNext(): " + o.toString());
            }
        });
    }
    public void combineLatestObservables(){
        Observable<Long> observable1 = Observable.interval(300, TimeUnit.MILLISECONDS)
                .take(7);

        Observable<Long> observable2 = Observable.interval(500, TimeUnit.MILLISECONDS)
                .take(7)
                .map(new Func1<Long, Long>() {
                    @Override
                    public Long call(Long aLong) {
                        return aLong + 100;
                    }
                });

        Observable.combineLatest(observable1, observable2, new Func2<Long, Long, String>() {
                    @Override
                    public String call(Long aLong, Long aLong2) {
                        return String.format("%s and %s", aLong, aLong2);
                    }
                }).subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println("onNext():" + s);
                    }
                });
    }

    public void withLatestFromObservable(){
        Observable<Long> observable1 = Observable.interval(300, TimeUnit.MILLISECONDS)
                .take(7);

        Observable<Long> observable2 = Observable.interval(500, TimeUnit.MILLISECONDS)
                .take(7).map(new Func1<Long, Long>() {
                    @Override
                    public Long call(Long aLong) {
                        return aLong + 100;
                    }
                });

        observable1.withLatestFrom(observable2, new Func2<Long, Long, Object>() {
            @Override
            public Object call(Long aLong, Long aLong2) {
                return String.format("%s and %s", aLong, aLong2);
            }
        }).subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                System.out.println("onNext(): " + o.toString());
            }
        });
    }
}
