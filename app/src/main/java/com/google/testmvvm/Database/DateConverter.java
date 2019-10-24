package com.google.testmvvm.Database;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class DateConverter  {
    //when written from database
    @TypeConverter
    public static Date toDate(Long timestamp){
        return timestamp ==null ?  null: new Date(timestamp);
    }

//when written into database
    @TypeConverter
    public static Long toTimestamp(Date date){
  return date==null ? null: date.getTime();
    }
}
