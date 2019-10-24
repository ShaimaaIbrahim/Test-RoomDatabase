package com.google.testmvvm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.google.testmvvm.Database.AppDataBase;
import com.google.testmvvm.Database.TaskEntry;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<TaskEntry>>tasks;

    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDataBase dataBase=AppDataBase.getInstance(this.getApplication());
        tasks=dataBase.taskDao().loadAllTask();
    }

    public LiveData<List<TaskEntry>> getTasks() {
        return tasks;
    }



}
