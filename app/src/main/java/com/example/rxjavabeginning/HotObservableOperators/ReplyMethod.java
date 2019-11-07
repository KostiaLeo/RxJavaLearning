package com.example.rxjavabeginning.HotObservableOperators;

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

    void subscriptionReply() {
        // Method .replay() is similar to .publish(), but
        // each new sub.-er retrieves all messages what he missed.
        // Method has option to define amount of stored items and storing time

        final ConnectableObservable<String> observableReply = Observable.from(albums).create(onSubscribe).subscribeOn(Schedulers.io()).replay();

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

