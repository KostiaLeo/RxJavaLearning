package com.example.rxjavabeginning.Subjects;

import android.os.Bundle;

import com.example.rxjavabeginning.R;
import com.example.rxjavabeginning.StaticObservableDefines;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subjects.AsyncSubject;

// AsyncSubject - one which emits ONLY last executing result even though task has been completed and subject has stopped his work
// It's definitely useful for checking any task completing which return just single result although operation has been finished

// It's also a few methods for this one:
//   hasValue() - it checks if the final result has already appeared
//   getValue() - get executing result :)


// UnicastSubject - subject you can subscribe just the one observer. Even though this observer has unsubscribed you can't subscribe another one
// It's allowed single observer, no more, throughout all subject work time

public class AsyncAndUnicastSubjects {
    Observable<String> observable = Observable.from(StaticObservableDefines.albums).create(StaticObservableDefines.onSubscribe).subscribeOn(Schedulers.io());

    final Observer<String> observer1 = StaticObservableDefines.observer1;
    final Observer<String> observer2 = StaticObservableDefines.observer2;

    final AsyncSubject<String> subject = AsyncSubject.create();

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        example();
//    }
//
//    void example() {
//        System.out.println("Subject has been subscribed");
//        final Subscription subjectSub = observable.subscribe(subject);
//        getWindow().getDecorView().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("first user has been subscribed");
//                subject.subscribe(observer1);

//    Explanation:
//                        We'll fetch LAST onNext result int the moment just subject HAS FINISHED his work
//                        So, first user will retrieve result when subject will has emitted all data and we'll get only "Everybody's Everything"

//            }
//        }, 2000);
//
//        getWindow().getDecorView().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("second user has been subscribed");
//                subject.subscribe(observer2);

//    Explanation:
//                      Second subscriber will get "Everybody's everything" as well as subject has already finished emitting
//                      and it's sending just last item
//
//            }
//        }, 15000);
//    }
}
