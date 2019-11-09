package com.example.rxjavabeginning;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

public class StaticObservableDefines {
    public static final Observer<String> observer1 = new Observer<String>() {
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

    public static final Observer<String> observer2 = new Observer<String>() {
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

    private static String[] albs = {"Lil Peep pt.1", "Live Forever", "Vertigo", "Crybaby", "Castles", "Hellboy", "Come Over When You're Sober pt.1", "Come Over When You're Sober pt.2", "Goth Angel Sinner", "Everybody's Everything"};
    public static List<String> albums = new ArrayList<>(Arrays.asList(albs));

    public static Observable.OnSubscribe<String> onSubscribe = new Observable.OnSubscribe<String>() {
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
}
