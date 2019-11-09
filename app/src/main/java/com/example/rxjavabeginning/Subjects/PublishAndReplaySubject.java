package com.example.rxjavabeginning.Subjects;

import com.example.rxjavabeginning.StaticObservableDefines;

import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

// Simple publish subject: it subscribes to multiple observables and emit data from them to own subscribers
// so, observers ain't gonna need subscribe to each observer, but instead could sub to only one subject

// ReplaySubject - it's essentially as though PublishSubject with ReplayObservable's distinguishes
// So exactly when new followers come they're retrieving all messages ones left

// ReplaySubject has following methods:
// - getValue() - receive last item
// - getValues() - receive all stored data
// - hasAnyValue() - is Subject storing any data
// - size() - amount storing data


public class PublishAndReplaySubject {

    Observable<String> observable = Observable.from(StaticObservableDefines.albums)
            .create(StaticObservableDefines.onSubscribe).subscribeOn(Schedulers.io());

    final Observer<String> observer1 = StaticObservableDefines.observer1;
    final Observer<String> observer2 = StaticObservableDefines.observer2;

    final PublishSubject<String> subject = PublishSubject.create();

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        example();
//    }

//    void example() {
//        System.out.println("Subject has been subscribed");
//        observable.subscribe(subject);
//
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
//        }, 4500);
//    }
}
