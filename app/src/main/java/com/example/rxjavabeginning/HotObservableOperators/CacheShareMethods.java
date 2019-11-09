package com.example.rxjavabeginning.HotObservableOperators;

import com.example.rxjavabeginning.StaticObservableDefines;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

// CACHE() = REPLAY().AUTOCONNECT()
// SHARE() = PUBLISH().REFCOUNT()

public class CacheShareMethods {

    // CACHE() = REPLAY().AUTOCONNECT()
    // SHARE() = PUBLISH().REFCOUNT()

    Subscription sub1, sub2;
    void subscriptionRefCount() {
        // cache() - method which start emitting on first sub.-er and send all messages for each new sub.-er (even ones new sub.-er missed)

        final Observable<String> observable = Observable.from(StaticObservableDefines.albums)
                .create(StaticObservableDefines.onSubscribe).subscribeOn(Schedulers.io()).cache();
        //final Subscription sub = observable.connect();

        System.out.println("Observable is connected");

        final Observer<String> observer1 = StaticObservableDefines.observer1;
        final Observer<String> observer2 = StaticObservableDefines.observer2;

//        getWindow().getDecorView().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("first user has been subscribed");
//                sub1 = observable.subscribe(observer1);
//            }
//        }, 1500);
//
//        getWindow().getDecorView().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("second user has been subscribed");
//                sub2 = observable.subscribe(observer2);
//            }
//        }, 3000);
//        getWindow().getDecorView().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("Off 1 observer");
//
//                //   observer1.onCompleted();
//                // observer2.onCompleted();
//                sub1.unsubscribe();
//            }
//        }, 6000);

//        getWindow().getDecorView().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("Off 2 observer");
//                sub2.unsubscribe();
//                //   observer1.onCompleted();
//                // observer2.onCompleted();
//            }
//        }, 8000);
//
//        getWindow().getDecorView().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("Sub1 repeat");
//                sub1 = observable.subscribe(observer1);
//                //   observer1.onCompleted();
//                // observer2.onCompleted();
//            }
//        }, 9000);
//
//        getWindow().getDecorView().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("Sub1 end2");
//                sub1.unsubscribe();
//                //   observer1.onCompleted();
//                // observer2.onCompleted();
//            }
//        }, 11000);
    }
}
