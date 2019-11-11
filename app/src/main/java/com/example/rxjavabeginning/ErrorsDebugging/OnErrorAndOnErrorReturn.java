package com.example.rxjavabeginning.ErrorsDebugging;

import rx.Observable;
import rx.functions.Func1;

public class OnErrorAndOnErrorReturn {

    public static void main(String[] args) {
        OnErrorAndOnErrorReturn o = new OnErrorAndOnErrorReturn();
        o.subscribeOnErrorReturn();
    }

    // It's pretty similar to onError but after defining Error it emits certain element(return in onErrorReturn.Func1.call returns)
    // and then calls onCompleted

    private void subscribeOnErrorReturn() {
        Observable<Long> observable = StaticsError.stringObservableData.map(new Func1<String, Long>() {
            @Override
            public Long call(String s) {
                return Long.parseLong(s);
            }
        }).onErrorReturn(new Func1<Throwable, Long>() {
            @Override
            public Long call(Throwable throwable) {
                System.out.println("Error - " + throwable);
                return 0L;
            }
        });
        StaticsError.subscribe(observable);
    }


    // It works approximately like simple try-catch block. It recognizes error and send onError event (=stop observable on last right item)
    private void subscribeSimpleOnError() {
        Observable<Long> observable = StaticsError.stringObservableData.map(new Func1<String, Long>() {
            @Override
            public Long call(String s) {
                return Long.parseLong(s);
            }
        });
        StaticsError.subscribe(observable);
    }
}