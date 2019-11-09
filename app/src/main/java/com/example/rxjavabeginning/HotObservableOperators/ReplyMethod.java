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
import rx.observables.ConnectableObservable;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class ReplyMethod {

    void subscriptionReply() {
        // Method .replay() is similar to .publish(), but
        // each new sub.-er retrieves all messages what he missed.
        // Method has option to define amount of stored items and storing time

        final ConnectableObservable<String> observableReply = Observable.from(StaticObservableDefines.albums)
                .create(StaticObservableDefines.onSubscribe).subscribeOn(Schedulers.io()).replay();

        System.out.println("Observable is connected");
        final Observer<String> observer1 = StaticObservableDefines.observer1;
        final Observer<String> observer2 = StaticObservableDefines.observer2;

        final Subscription sub1 = observableReply.subscribe(observer1);
        final Subscription sub2 = observableReply.subscribe(observer2);
        final CompositeSubscription composeForAutoConnect = new CompositeSubscription();
        composeForAutoConnect.add(sub1);
        composeForAutoConnect.add(sub2);

//        getWindow().getDecorView().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("first user has been subscribed");
//                observable.subscribe(observer1);
//            }
//        }, 2000);
//
//        getWindow().getDecorView().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("second user has been subscribed");
//                observable.subscribe(observer2);
//            }
//        }, 4500);
//        getWindow().getDecorView().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("Now we're gonna off a hot observable");
//                composeForAutoConnect.unsubscribe();
//            }
//        }, 8000);
//    }
    }
}

