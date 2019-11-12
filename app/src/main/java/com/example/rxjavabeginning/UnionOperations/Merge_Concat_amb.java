package com.example.rxjavabeginning.UnionOperations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;
import rx.functions.Func1;

public class Merge_Concat_amb {
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

    Observable<Long> observable1 = Observable.interval(300, TimeUnit.MILLISECONDS)
            .take(10);

    Observable<Long> observable2 = Observable.interval(500, TimeUnit.MILLISECONDS)
            .take(10)
            .map(new Func1<Long, Long>() {
                @Override
                public Long call(Long aLong) {
                    return aLong + 100;
                }
            });

    public void mergeTwoObservables(){
        observable1.mergeWith(observable2);
    }

    public void mergeMultipleObservbles(){
        Observable<Long>[] observableArray = new Observable[] {observable1, observable2};
        Observable.merge(observableArray, 1).subscribe(observer);
    }

    public void concatObservables(){
        observable1.concatWith(observable2)
                .subscribe(observer);
        // concat multiple:
        //Observer<Data> observableData = Observable
        // .concat(obs1, obs2, obs3).first(); (first() means we finish sequence after first positive result)
    }
    public void ambObservables(){
        Observable.amb(observable1, observable2).subscribe(observer);
    }
}
