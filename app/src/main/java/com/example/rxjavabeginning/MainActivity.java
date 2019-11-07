package com.example.rxjavabeginning;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action;
import rx.functions.Action1;
import rx.observables.ConnectableObservable;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {

    String[] albs = {"Lil Peep pt.1", "Live Forever", "Vertigo", "Crybaby", "Hellboy", "Come Over When You're Sober pt.1", "Come Over When You're Sober pt.2", "Goth Angel Sinner", "Everybody's Everything"};
    final List<String> albums = new ArrayList<>(Arrays.asList(albs));

// ---------- OnSubscribe action defining - parameter for creating observable
    Observable.OnSubscribe<String> onSubscribe = new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            for (String album : albums) {
                if(subscriber.isUnsubscribed()) return;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// ------- creating own cold observable - updates are sending to each user after
// ------- each one is subscribed. While observer doesn't have subscribers it
// ------- isn't active

        // next step - cold observable & observer initialization
        Observable<String> observable = Observable.from(albums).create(onSubscribe).subscribeOn(Schedulers.io());

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("Complete");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onNext(String s) {
                System.out.println("Album '" + s + "'");
            }
        };
//        final Subscription subscription = observable.subscribe(observer);
//
//        getWindow().getDecorView().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("unsubscribe");
//                subscription.unsubscribe();
//            }
//        }, 5000);
        simplePublishHotObservable();
    }

    Subscription sub1, sub2;


    void simplePublishHotObservable(){
// -------- creating hot observable - it's seems to classical Observer Pattern
// -------- Observable exist on it's own and emit data not minding about subscribers
        final ConnectableObservable<String> observable = Observable.from(albums).create(onSubscribe).subscribeOn(Schedulers.io()).publish();
        final Subscription sub = observable.connect();

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


        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println("first user has been subscribed");
                observable.subscribe(observer1);
            }
        }, 2000);

        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println("second user has been subscribed");
                observable.subscribe(observer2);
            }
        }, 4500);
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println("Now we're gonna off a hot observable");
                sub.unsubscribe();
            }
        }, 8000);
    }
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
