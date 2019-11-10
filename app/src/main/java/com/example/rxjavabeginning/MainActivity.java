package com.example.rxjavabeginning;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;

public class MainActivity extends AppCompatActivity {

//    Observable<String> observable = Observable.from(StaticObservableDefines.albums).create(StaticObservableDefines.onSubscribe).subscribeOn(Schedulers.io());

//    final Observer<String> observer1 = StaticObservableDefines.observer1;
//    final Observer<String> observer2 = StaticObservableDefines.observer2;

    final PublishSubject<Long> subject = PublishSubject.create();

    final SerializedSubject<Long, Long> serializedSubject = new SerializedSubject<>(subject);
//    final SerializedSubject<Long, Long> serializedSubject = subject.toSerialized(); - an equal form for previous serializedSubject declaration

    final Action1<Long> action = new Action1<Long>() {
        private long sum = 0;
        @Override
        public void call(Long aLong) {
            sum += aLong;
        }

        @Override
        public String toString() {
            return "sum = " + sum;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        example();
    }

    void example() {
        subject.subscribe(action);

        new Thread() {
            @Override
            public void run() {
                super.run();
                for (int i = 0; i < 100000; i++) {
                    serializedSubject.onNext(1L);
                }
                System.out.println("First thread done");
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                super.run();
                for (int i = 0; i < 100000; i++) {
                    serializedSubject.onNext(1L);
                }
                System.out.println("Second thread done");
            }
        }.start();

        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println(action.toString());
            }
        }, 5000);

//        System.out.println("Subject has been subscribed");
//        final Subscription subjectSub = observable.subscribe(subject);
//        getWindow().getDecorView().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("first user has been subscribed");
//                subject.subscribe(observer1);
//            }
//        }, 2000);
//
//        getWindow().getDecorView().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("second user has been subscribed");
//                subject.subscribe(observer2);
//            }
//        }, 15000);
    }
}