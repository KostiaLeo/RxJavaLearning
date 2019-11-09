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
import rx.subscriptions.CompositeSubscription;

public class AutoConnect {

    void subscriptionAutoConnect() {
        // Also it's method .autoConnect(int amountOfSubscribers(parameter isn't required)) which allows to run observable from define subscribers amount signed
        // (return Observable(not ConnactableObservable)), cuz we don't need connect() method as we emit data since certain sub.-er'll have done

        final Observable<String> observableAutoConnect = Observable.from(StaticObservableDefines.albums).create(StaticObservableDefines.onSubscribe)
                .subscribeOn(Schedulers.io()).publish().autoConnect(2);

        System.out.println("Observable is connected");
        final Observer<String> observer1 = StaticObservableDefines.observer1;
        final Observer<String> observer2 = StaticObservableDefines.observer2;

        final Subscription sub1 = observableAutoConnect.subscribe(observer1);
        final Subscription sub2 = observableAutoConnect.subscribe(observer2);
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
