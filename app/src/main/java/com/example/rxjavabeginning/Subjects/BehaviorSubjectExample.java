package com.example.rxjavabeginning.Subjects;

import android.os.Bundle;

import com.example.rxjavabeginning.R;
import com.example.rxjavabeginning.StaticObservableDefines;
import rx.subjects.BehaviorSubject;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

// BehaviorSubject's distinguishing feature is default buffer
// So, matter is while subject hasn't subscribed any observable it'll emit to own subscribers his default item
// Since Object has subscribed any observable it shares to own subs last item he has got from ones

// Good BehaviorSubject usage practice is showing status for example. On user's subscription he's gonna get either default value or last one
// and then it will retrieve new values according to status's changes

public class BehaviorSubjectExample {
    Observable<String> observable = Observable.from(StaticObservableDefines.albums).create
            (StaticObservableDefines.onSubscribe).subscribeOn(Schedulers.io());

    final Observer<String> observer1 = StaticObservableDefines.observer1;
    final Observer<String> observer2 = StaticObservableDefines.observer2;

    final BehaviorSubject<String> subject = BehaviorSubject.create("Now you're gonna receive Peep's albums :)");

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        example();
//    }
//
//    void example() {
//        System.out.println("Subject has been subscribed");
//        subject.subscribe(observer1); // it's actually not a mistake, we really firstly
//                                         subscribe observer to subject for getting default item from BehaviorSubject's buffer
//                                         while one doesn't sub any observable

//        getWindow().getDecorView().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("first user has been subscribed");
//                observable.subscribe(subject);

//                     !!! and now we sub subj to observable and observer1 is getting data since the moment !!!

//            }
//        }, 2000);
//
//        getWindow().getDecorView().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("second user has been subscribed");
//                subject.subscribe(observer2);

//                   !!! sub observer2 so he's retrieving last element from buffer and then get other items !!!

//            }
//        }, 4500);
//    }
}
