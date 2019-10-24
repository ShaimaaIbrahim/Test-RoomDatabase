package com.google.testmvvm.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.content.Context;
import android.util.Log;
import android.arch.persistence.room.TypeConverters;


@Database(entities = {TaskEntry.class} , version = 1 ,exportSchema = false)
@TypeConverters(DateConverter.class)

public abstract class AppDataBase extends RoomDatabase {

    private static final String TAG = "AppDataBase";
    private static final Object LOCK=new Object();
    private static final String DATABASE_NAME="todolist";
    private static AppDataBase SINSTANCE;

    public static AppDataBase getInstance(Context context){
  if (SINSTANCE==null){
      synchronized (LOCK){
          Log.d(TAG, "getInstance: creating database..");
          SINSTANCE= Room.databaseBuilder(context.getApplicationContext(),AppDataBase.class,
                  AppDataBase.DATABASE_NAME
          ).build();
      }
  }
        Log.d(TAG, "getInstance: Getting database Instance");
  return SINSTANCE;

    }

    public abstract TaskDao taskDao();
}
