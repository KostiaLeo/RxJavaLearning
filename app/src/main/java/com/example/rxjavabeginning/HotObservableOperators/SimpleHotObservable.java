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

public class SimpleHotObservable {

// ------- creating own cold observable - updates are sending to each user after
// ------- each one is subscribed. While observer doesn't have subscribers it
// ------- isn't active

        // next step - cold observable & observer initialization
        Observable<String> observable = Observable.from(StaticObservableDefines.albums).create(StaticObservableDefines.onSubscribe).subscribeOn(Schedulers.io());

        Observer<String> observer = StaticObservableDefines.observer1;
//        final Subscription subscription = observable.subscribe(observer);
//
//        getWindow().getDecorView().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("unsubscribe");
//                subscription.unsubscribe();
//            }
//        }, 5000);

    Subscription sub1, sub2;


    void simplePublishHotObservable() {
// -------- creating hot observable - it's seems to classical Observer Pattern
// -------- Observable exist on it's own and emit data not minding about subscribers

        final ConnectableObservable<String> observable = Observable.from(StaticObservableDefines.albums)
                .create(StaticObservableDefines.onSubscribe).subscribeOn(Schedulers.io()).publish();
        final Subscription sub = observable.connect();

        System.out.println("Observable is connected");
        final Observer<String> observer1 = StaticObservableDefines.observer1;
        final Observer<String> observer2 = StaticObservableDefines.observer2;
    }

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
//                sub.unsubscribe();
//            }
//        }, 8000);
//    }
}
































//------------------ Threads ---------------------


// First way to create Thread
//class FirstThread extends Thread{
//    @Override
//    public void run() {
//        System.out.println("Peep by 1way" + getName());
//    }
//}
//
////Second way
//class PeepThreadWithRunnable implements Runnable{
//    @Override
//    public void run() {
//        System.out.println("Peep by 2way");
//    }
//}


//        //for (int i = 0; i < 5; i++) {
//            //1 way continuing
//            FirstThread peepThread = new FirstThread();
//            peepThread.start();
//
//            //2 way continuing
//            PeepThreadWithRunnable peep2thread = new PeepThreadWithRunnable();
//            Thread thread = new Thread(peep2thread);
//            thread.start();
//
//            //3 way full
//            Thread shortThread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println("Peep by 3way");
//                }
//            });
//            shortThread.start();
//        }

