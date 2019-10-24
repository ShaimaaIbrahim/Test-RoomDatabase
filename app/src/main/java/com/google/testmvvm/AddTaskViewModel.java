package com.google.testmvvm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.google.testmvvm.Database.AppDataBase;
import com.google.testmvvm.Database.TaskEntry;

public class AddTaskViewModel extends ViewModel {

    private LiveData<TaskEntry>task;


    public LiveData<TaskEntry> getTask() {

        return task;
    }

    public AddTaskViewModel(AppDataBase dataBase ,int mTaskId) {
        task=dataBase.taskDao().loadTaskById(mTaskId);

    }
}
