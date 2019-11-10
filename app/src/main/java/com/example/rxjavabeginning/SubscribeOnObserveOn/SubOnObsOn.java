package com.example.rxjavabeginning.SubscribeOnObserveOn;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rxjavabeginning.R;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

public class SubOnObsOn {

// SubscribeOn - method which defines new thread Scheduler for executing Observable.onSubscribe's call() method which generate and emit data

// Schedulers: io() - for executing a lot of pretty light operations(no threads amount limit);
//             computation() - if you gotta deal with complicated calculations you should use this one. But it has thread-amount limit (quantity of CPU cores)
//             newThread() - scheduler with random new Thread

// observeOn - all next Observable operators will be managing in other defined scheduler (if you're about to share to subscribers data in main thread)
//     you should use AndroidSchedulers.mainThread (it's actually from RxAndroid library, implementation already has done in Gradle)

// but it's not only the one observeOn's feature. Check the following explanation:
//     Observable observable = createOperator(...)
//    .subscribeOn(scheduler)                         \    we're defining scheduler for subscribeOn
//    .operator1(...)                                  |-> so, all next operators will have been executing in the scheduler
//    .operator2(...)                                 /    and observers will retrieve data in subscribeOn's scheduler

//    Observable observable = createOperator(...).operator1(...) .  .  . .subscribeOn(scheduler); - it's equals cuz we don't matter when to declare
//                                                                                          emitting, it's always single

// But it's pretty interesting to deal with observeOn:
//         Observable observable = createOperator(...)
//              .subscribeOn(scheduler)                 \    we've defined observeOn before other operators
//              .observeOn(scheduler1)                   |-> so it means all operators which is going after observeOn
//              .operator1(...)                         /    will execute in scheduler scheduler1
//              .operator2(...);

// Let's analyze our code:
//      Observable<String> observable = Observable.from(StaticsSubObsOn.cowys)
//      .create(StaticsSubObsOn.onSubscribe)
//            .subscribeOn(Schedulers.io())        -> creation declares new Scheduler.io() (let it be calling Scheduler0)
//            .observeOn(Schedulers.io())          -> definition first observeOn's scheduler (let it be calling Scheduler1)
//            .take(6)                             -> operator take() is executing in Scheduler1 (already not Scheduler0)
//            .observeOn(Schedulers.io())          -> definition second observeOn's scheduler (let it be calling Scheduler2)
//            .zipWith(Observable.from(new Integer[]{1, 2, 3, 4, 5, 6}), zipSongNumberAndName) -> operator zipWith() is executing in Scheduler2
//            .observeOn(AndroidSchedulers.mainThread()); -> finally onNext() for observers's getting data will be completed in main thread


    public class SubOnObsOnActivity extends AppCompatActivity {

        final Observer<String> observer1 = StaticsSubObsOn.observer;

        Func2<String, Integer, String> zipSongNumberAndName = new Func2<String, Integer, String>() {
            @Override
            public String call(String s, Integer integer) {
                System.out.println("zip-func operator " + integer + ". [" + Thread.currentThread().getName() + "]");
                return integer + ": " + s;
            }
        };

        Observable<String> observable = Observable.from(StaticsSubObsOn.cowys).create(StaticsSubObsOn.onSubscribe)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .take(6)
                .observeOn(Schedulers.io())
                .zipWith(Observable.from(new Integer[]{1, 2, 3, 4, 5, 6}), zipSongNumberAndName)
                .observeOn(AndroidSchedulers.mainThread());

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            example();
        }

        void example() {
            System.out.println("Subscribe creation " + "[" + Thread.currentThread().getName() + "]");
            observable.subscribe(observer1);
            System.out.println("Done " + "[" + Thread.currentThread().getName() + "]");
        }
    }
}
