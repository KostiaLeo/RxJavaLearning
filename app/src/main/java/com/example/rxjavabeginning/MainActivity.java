package com.example.rxjavabeginning;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

public class MainActivity extends AppCompatActivity {

    Observable<String> observable = Observable.from(StaticObservableDefines.albums).create(StaticObservableDefines.onSubscribe).subscribeOn(Schedulers.io());

    final Observer<String> observer1 = StaticObservableDefines.observer1;
    final Observer<String> observer2 = StaticObservableDefines.observer2;

    final BehaviorSubject<String> subject = BehaviorSubject.create("Now you're gonna receive Peep's albums :)");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        example();
    }

    void example() {
        System.out.println("Subject has been subscribed");
        subject.subscribe(observer1);
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println("first user has been subscribed");
                observable.subscribe(subject);
            }
        }, 2000);

        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println("second user has been subscribed");
                subject.subscribe(observer2);
            }
        }, 4500);

    }
}
