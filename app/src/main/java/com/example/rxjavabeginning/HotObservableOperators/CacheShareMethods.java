package com.example.rxjavabeginning.HotObservableOperators;

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
    String[] albs = {"Lil Peep pt.1", "Live Forever", "Vertigo", "Crybaby", "Hellboy", "Come Over When You're Sober pt.1", "Come Over When You're Sober pt.2", "Goth Angel Sinner", "Everybody's Everything"};
    final List<String> albums = new ArrayList<>(Arrays.asList(albs));

    // ---------- OnSubscribe action defining - parameter for creating observable
    Observable.OnSubscribe<String> onSubscribe = new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            for (String album : albums) {
                if (subscriber.isUnsubscribed()) return;
                try {
                    TimeUnit.SECONDS.sleep(1);
                    subscriber.onNext(album);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (subscriber.isUnsubscribed()) return;
            subscriber.onCompleted();
        }
    };

    // CACHE() = REPLAY().AUTOCONNECT()
    // SHARE() = PUBLISH().REFCOUNT()

    Subscription sub1, sub2;
    void subscriptionRefCount() {
        // cache() - method which start emitting on first sub.-er and send all messages for each new sub.-er (even ones new sub.-er missed)

        final Observable<String> observable = Observable.from(albums).create(onSubscribe).subscribeOn(Schedulers.io()).cache();
        //final Subscription sub = observable.connect();

        System.out.println("Observable is connected");
        final Observer<String> observer1 = new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("Complete for first observer");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onNext(String s) {
                System.out.println("Album '" + s + "' is delivered to first subscriber");
            }
        };

        final Observer<String> observer2 = new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("Complete for second observer");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onNext(String s) {
                System.out.println("Album '" + s + "' is delivered to second subscriber");
            }
        };

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
