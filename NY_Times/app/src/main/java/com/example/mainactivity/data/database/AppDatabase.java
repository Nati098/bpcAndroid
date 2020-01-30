package com.example.mainactivity.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {NewsEntity.class}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "NewsRoomDb.db";

    private static AppDatabase singleton;

    public abstract NewsDao newsDao();

    public static AppDatabase getAppDatabase(Context context){
        if (singleton == null){
            synchronized (AppDatabase.class){
                if (singleton == null) {
                    singleton = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                            .allowMainThreadQueries().build();
                }
            }
        }
        return singleton;
    }






}
