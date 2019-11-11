package com.example.rxjavabeginning.ErrorsDebugging;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;


// retry() - operator which for retrying data emitting.
// I.e. in a case of error your observer can subscribe again and try retrieve data
// It could be useful, for example, with inconstant connection

public class Retry {
    public static void main(String[] args) {
        Retry retry = new Retry();
        retry.expandedRetry();
    }

    private void simpleRetry() {
        Observable<Long> observable = StaticsError.stringObservableData
                .map(new Func1<String, Long>() {
                    @Override
                    public Long call(String s) {
                        return Long.parseLong(s);
                    }
                }).retry(2); // you also can use .retry() without parameters but it might be infinity-call
                             // so it's definitely wrong way when error probability is extremely high
                             // from code we can notice it'll be 3 retries (not 2 cuz 1 operation is not retrying)

        StaticsError.subscribe(observable);
    }

// It's one more variant how to use retry according to error and tries amount
    private void expandedRetry(){
        Observable<Long> observable = StaticsError.stringObservableData
                .map(new Func1<String, Long>() {
                    @Override
                    public Long call(String s) {
                        return Long.parseLong(s);
                    }
                }).retry(new Func2<Integer, Throwable, Boolean>() {
                    @Override
                    public Boolean call(Integer retryAmount, Throwable throwable) {
                        System.out.println("Error: " + throwable); // It shows our error event as well
                        return retryAmount < 3; // and return boolean, checking count of tries
                    }                           // Thus we're gonna get onError after 2 retry calling
                });
        StaticsError.subscribe(observable);
    }
}