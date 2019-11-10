package com.example.rxjavabeginning.SubscribeOnObserveOn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

public class StaticsSubObsOn {

    public static final Observer<String> observer = new Observer<String>() {
        @Override
        public void onCompleted() {
            System.out.println("onCompleted() for observer: [" + Thread.currentThread().getName() + "]");
        }
        @Override
        public void onError(Throwable e) {
            System.out.println(e.getMessage());
        }
        @Override
        public void onNext(String s) {
            System.out.println("onNext() for observer: Song " + s + " is delivered to subscriber. [" + Thread.currentThread().getName() + "]");
        }
    };

    public static List<String> cowys = new ArrayList<>
            (Arrays.asList("Benz Truck", "Save That Shit", "Awful Things", "Better Off", "U Said", "Problems", "16 Lines(woops)"));

    public static Observable.OnSubscribe<String> onSubscribe = new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            System.out.println("Observable.call(), creation and emitting items: [" + Thread.currentThread().getName() + "]");
            for (String album : cowys) {
                if (subscriber.isUnsubscribed()) return;
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
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
