package com.example.abc.myapplication20;

import android.content.Intent;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {
    
    public static final String TAG="text";
    Integer i=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //快速创建被观察者
         rxjavaCreate();//最基础的创建符
         rxjavaJust();//快速创建
         rxjavaArrayFrom();//数组
         rxjavaFromIterable();//集合

        //延迟创建
        rxjavaDefer();//动态创建
        rxjavaTimer();//延时发送，一般用于检测
        rxjavaInterval();//每隔指定时间就发送事件
        rxjavaIntervalRange();//每隔指定时间 就发送 事件，可指定发送的数据的数量
        rxjavarange();//发送事件的特点：连续发送 1个事件序列，可指定范围,无延迟发送事件
    }
    
    public void rxjavaCreate()
    {
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> e) {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
                
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: ");
                
            }

            @Override
            public void onNext(Integer value) {

                Log.d(TAG, "onNext: "+value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");

            }
        });
    }
    public void rxjavaJust()
    {
        Observable.just(1,2,3,4).safeSubscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "onNext: "+value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });
    }
    public void rxjavaArrayFrom()
    {
        Integer[] a={1,2,3,4};

        Observable.fromArray(a).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "onNext: "+ value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });
    }
    public void rxjavaFromIterable()
    {
        List<Integer> list=new ArrayList<>();

        list.add(1);
        list.add(2);
        list.add(3);

        Observable.fromIterable(list).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "onNext: "+value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });
    }
    public void rxjavaDefer()
    {



        // 2. 通过defer 定义被观察者对象
        // 注：此时被观察者对象还没创建
        Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<Integer>>() {

            @Override
            public ObservableSource<Integer> call() throws Exception {
                return Observable.just(i);
            }
        });


            i = 15;


            // 注：此时，才会调用defer（）创建被观察者对象（Observable）
            observable.subscribe(new Observer<Integer>() {

                @Override
                public void onSubscribe(Disposable d) {
                    Log.d(TAG, "开始采用subscribe连接");
                }

                @Override
                public void onNext(Integer value) {
                    Log.d(TAG, "接收到的整数是"+ value  );
                }

                @Override
                public void onError(Throwable e) {
                    Log.d(TAG, "对Error事件作出响应");
                }

                @Override
                public void onComplete() {
                    Log.d(TAG, "对Complete事件作出响应");
                }
            });

    }
    public void  rxjavaTimer()
    {
        Observable.timer(2, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(Long value) {
                Log.d(TAG, "onNext: "+value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });
    }
    public void rxjavaInterval()
    {
        Observable.interval(2,1,TimeUnit.SECONDS).subscribe(new Observer<Long>() {
            Disposable disposable;
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: ");
                disposable=d;
            }

            @Override
            public void onNext(Long value) {
                Log.d(TAG, "onNext: "+value);
                if(value==12)
                {
                    disposable.dispose();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });
    }

    public void rxjavaIntervalRange()
    {
        Observable.intervalRange(3,10,2,1,TimeUnit.SECONDS).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(Long value) {
                Log.d(TAG, "onNext: "+value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });
    }

    public void rxjavarange()
    {
        Observable.range(4,10).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "onNext: "+value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });
    }
}
