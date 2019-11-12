package com.example.rxjavabeginning;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.rxjavabeginning.SubscribeOnObserveOn.StaticsSubObsOn;
import com.example.rxjavabeginning.UnionOperations.Merge_Concat_amb;
import com.example.rxjavabeginning.UnionOperations.Zip_combineLatest_withLatestFrom;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Zip_combineLatest_withLatestFrom z = new Zip_combineLatest_withLatestFrom();
        z.withLatestFromObservable();
    }
}