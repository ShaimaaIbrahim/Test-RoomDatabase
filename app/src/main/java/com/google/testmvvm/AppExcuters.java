package com.google.testmvvm;

import android.os.Handler;
import android.os.Looper;
import android.os.NetworkOnMainThreadException;
import android.support.annotation.MainThread;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExcuters {
    private static final Object LOCK = new Object();
    private static AppExcuters sInstance;
    private final Executor DeskIo;
    private final Executor MainThread;
    private final Executor NetworkId;

    public AppExcuters(Executor deskIo ,Executor networkId,Executor mainThread) {
               this.DeskIo=deskIo;
               this.NetworkId=networkId;
        this.MainThread=mainThread;
    }

    public static AppExcuters getInstance(){
        if (sInstance==null){
            synchronized (LOCK){
               sInstance = new AppExcuters(Executors.newSingleThreadExecutor()
                        ,Executors.newFixedThreadPool(3), new MainThreadExcutor());
            }
        }
        return sInstance;
    }

    public Executor diskIo(){
        return DeskIo;
    }

    public Executor getNetworkId(){
        return NetworkId;
    }
    public Executor getMainThread(){
        return MainThread;
    }

    private static class MainThreadExcutor implements Executor {
private Handler mainThreadHandler =new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable command) {
mainThreadHandler.post(command);
        }
    }
}

