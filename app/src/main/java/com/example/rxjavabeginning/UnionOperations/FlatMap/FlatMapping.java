package com.example.rxjavabeginning.UnionOperations.FlatMap;

import rx.Observable;

public class FlatMapping {
    // Lesson for this topic
    // https://startandroid.ru/ru/courses/rxjava/19-course/rxjava/457-urok-8-operatory-obedinenija.html

    // Observable<UserGroup> userGroupObservable = ...;
    // Let suppose we retrieve list of userGroups
    // from outside (db, server etc.) and we want to get all users from group in 1 observable
    // if we try to apply map for return List<>:
    //      Observable<List<String>> observable = userGroupObservable
    //        .map(new Func1<UserGroup, List<String>>() {
    //            @Override
    //            public List<String> call(UserGroup userGroup) {
    //                return userGroup.getUsers();
    //            }
    //        });
    // we'll receive:
    //  onNext [User 1, User 2, User 3, User 4, User 5]
    //  onNext [User 6, User 7, User 8, User 9, User 10]
    //  onCompleted

    // But we actually need Observable which emit users one-by-one
    // So let's try return Observable:
    //      Observable<Observable<String>> observable = userGroupObservable
    //        .map(new Func1<UserGroup, Observable<String>>() {
    //            @Override
    //            public Observable<String> call(UserGroup userGroup) {
    //                return Observable.from(userGroup.getUsers());
    //            }
    //        });

    // Okay, it seems to right way, but logs say other:
    //      onNext rx.Observable@4f18411
    //      onNext rx.Observable@2e44368
    //      onCompleted

    // Therefore we need operator 'map' not to just convert UserGroup to Observable<>, but it should
    // to take items out from received Observable<> and send it to main thread,
    // that's hy we use 'flatMap', thus finally we get Observable<String>
    // In this one are locating all users from each userGroup

    // Code:
    //      Observable<String> observable = userGroupObservable
    //        .flatMap(new Func1<UserGroup, Observable<String>>() {
    //            @Override
    //            public Observable<String> call(UserGroup userGroup) {
    //                return Observable.from(userGroup.getUsers());
    //            }
    //        }(*optional: `, 1`)); you can set maxConcurrent - all usersGroup(=Observable)
    //        will show consistently and each local user as well

    // So, result of following code will be:
    // onNext User 1
    // onNext User 2
    // onNext User 3
    // onNext User 4
    //     ...

// concatMap()- flatMap with maxConcurrent = 1

// interval by concatMap manual definition:

    //Observable<String> observable1 = Observable.just("A", "B", "C", "D", "E");
    //observable1
    //        .concatMap(new Func1<String, Observable<String>>() {
    //            @Override
    //            public Observable<String> call(String s) {
    //                return Observable.just(s).delay(100, TimeUnit.MILLISECONDS);
    //            }
    //        })
}
