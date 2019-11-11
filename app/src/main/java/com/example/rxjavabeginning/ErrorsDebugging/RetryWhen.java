package com.example.rxjavabeginning.ErrorsDebugging;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;


// actually retryWhen() operator is as though retry, but in more expanded and manual mode
// created for the same purposes as like as retry()

public class RetryWhen {
    private Observable<Long> observableMain;

    public static void main(String[] args) {
        RetryWhen retryWhen = new RetryWhen();
        retryWhen.simpleRetryWhen();
    }

    private void simpleRetryWhen(){
        observableMain = StaticsError.stringObservableData.map(new Func1<String, Long>() {
            @Override
            public Long call(String s) {
                return Long.parseLong(s);
            }
        }).retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
            @Override
            public Observable<?> call(Observable<? extends Throwable> observableErrors) {
                System.out.println("RetryWhen");
                return observableErrors.take(3);
            }
        });
        StaticsError.subscribe(observableMain);
    }

// Prev example works by the following way:
//      main point is creation from observableError(which throwing errors)
//      our observableRetry which will define event for mainObservable. How?
//          As we could see in parameter we're setting Func1<> which return observable with T-parameter,
//          so it means observable-type doesn't matter and if we return take(), zip() or default methods
//          it equals to return onNext, thus according to this we should retry emitting
//          if our tries was failed we get onCompleted


    private void expandedByErrorShowRetryWhen() {
        observableMain = StaticsError.stringObservableData.map(new Func1<String, Long>() {
            @Override
            public Long call(String s) {
                return Long.parseLong(s);
            }
        }).retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
            @Override
            public Observable<?> call(Observable<? extends Throwable> observableErrors) {
                System.out.println("RetryWhen");
                return observableErrors.zipWith(Observable.range(1, 3), new Func2<Throwable, Integer, Observable>() {
                    @Override
                    public Observable call(Throwable throwable, Integer integer) {
                        if (integer < 3) {
                            return Observable.just(0L);
                        } else {
                            return Observable.error(throwable);
                        }
                    }
                }).flatMap(new Func1<Observable, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable observable) {
                        return observable;
                    }
                });
            }
        });
        StaticsError.subscribe(observableMain);
    }

// In expanded variant we're able to deal manually with error throwing by zipping (in this case)
// throwable from observableError and Integer from range() and show failed event.
// but we're validating count of tries as well. If it's within the limits of ones
// we retry again returning onNext command via Observable.just(0L),
// otherwise we send onError by Observable.error(throwable) usage
}