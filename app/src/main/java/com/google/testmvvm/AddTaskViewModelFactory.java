package com.google.testmvvm;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.google.testmvvm.Database.AppDataBase;

public class AddTaskViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final  AppDataBase dataBase;
    private final  int mTaskId;

    public AddTaskViewModelFactory(AppDataBase dataBase ,int mTaskId) {
        this.dataBase = dataBase;
        this.mTaskId=mTaskId;

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddTaskViewModel(dataBase,mTaskId);

    }
}
