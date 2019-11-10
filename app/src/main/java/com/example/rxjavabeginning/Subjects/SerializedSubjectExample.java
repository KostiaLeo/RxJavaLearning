package com.example.rxjavabeginning.Subjects;

import rx.functions.Action1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;

// SerializedSubject - subject for dealing with multithreading which guarantees us thread-proof working with subjects and observers
// It works like `synchronized` keyword as ensure safety and doesn't allow multiple threads to deal with variables/actions in the same time

// Code example:

// if we don't make subject serialized or not wrap it into synchronized block we're gonna lost some information because of
// multi-threading non-safety. I.e. without serialized/synchronized we're getting ~190000 instead of full required 200000

// useful methods:
//     toSerialized() - creates SerializedSubject wrapper for Subject
//     hasObservers() - show does Subject have observers
//     asObservable() - returns Observable-wrapper for subject. You're gonna need it if you want to present Subject for external
//       subscribers. But if you'll take out Subject so everyone will be able to call his onNext(), onError() and onComplete() methods.
//       Consequently it could brake upset class working logic. Thus you can return simply Observable so external objects just may subscribe


public class SerializedSubjectExample {

    final PublishSubject<Long> subject = PublishSubject.create(); // Creation subject

    final SerializedSubject<Long, Long> serializedSubject = new SerializedSubject<>(subject); // serialized wrapper
//    final SerializedSubject<Long, Long> serializedSubject = subject.toSerialized(); - an equal form for previous serializedSubject declaration

    final Action1<Long> action = new Action1<Long>() {
        private long sum = 0;
        @Override
        public void call(Long aLong) {
            sum += aLong;
        }


//  Essentially the same thread-proof example but for certain action:

//        final Object lock = new Object();
//        @Override
//        public void call(Long aLong) {
//            synchronized (lock) {
//                sum += aLong;
//            }
//        }

        @Override
        public String toString() {
            return "sum = " + sum;
        }
    };

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        example();
//    }

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

//        getWindow().getDecorView().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println(action.toString());
//            }
//        }, 5000);
    }
}
