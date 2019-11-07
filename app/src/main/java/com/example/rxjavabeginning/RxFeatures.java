package com.example.rxjavabeginning;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Action1;

public class RxFeatures {

//        simpleObserverObservable();
//        System.out.println("");
//        actionAndObservable();
//        Observable.fromCallable(new CallableLongAction("Star Shopping")).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<String>() {
//            @Override
//            public void call(String s) {
//                System.out.println("onCall -> " + s);
//}
//        });
//        subscription();

    void subscription(){
        Observable<Long> observable = Observable.interval(1, TimeUnit.SECONDS);
        Action1<Long> action1 = new Action1<Long>() {
            @Override
            public void call(Long s) {
                System.out.println(s);
            }
        };

//        final Subscription subscription = observable.subscribe(action1);
//        getWindow().getDecorView().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("Unsubscribe");
//                subscription.unsubscribe();
//            }
//        }, 5000);
    }

    void actionAndObservable(){
        Observable<String> observable = Observable.from(new String[]{"Hellboy", "C.O.W.Y.S.pt1", "C.O.W.Y.S.pt2", "Crybaby", "Live Forever"});
        Action1 action = new Action1() {
            @Override
            public void call(Object o) {
                System.out.println("Album -> " + o);
            }
        };
        observable.subscribe(action);
        //actually action is similar to observer but provides only onNext method(calling as "call" instead)
    }



    void simpleObserverObservable(){
        Observable<String> observable = Observable.from(new String[]{"Hellboy", "C.O.W.Y.S.pt1", "C.O.W.Y.S.pt2", "Crybaby", "Live Forever"});
        Observer<String> lilPeepObserver = new Observer<String>() {

            @Override
            public void onNext(String s) {
                System.out.println("Album '" + s + "'");
            }

            @Override
            public void onCompleted() {
                System.out.println("That`s all I have");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Smth`s wrong");
            }
        };
        observable.subscribe(lilPeepObserver);
    }
}

class CallableLongAction implements Callable<String> {

    private final String data;

    public CallableLongAction(String data) {
        this.data = data;
    }

    @Override
    public String call() throws Exception {
        return longAction(data);
    }

    private String longAction(String s){
        System.out.println("LongAction");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return s.toUpperCase();
    }

}
