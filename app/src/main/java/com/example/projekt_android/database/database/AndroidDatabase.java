package com.example.projekt_android.database.database;


import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.projekt_android.database.entity.NewsEntity;
import com.example.projekt_android.database.entity.UserEntity;
import com.example.projekt_android.database.repository.NewsEntityDAO;
import com.example.projekt_android.database.repository.UserEntityDAO;

@androidx.room.Database(entities = {NewsEntity.class, UserEntity.class}, version = 1, exportSchema = false)
public abstract class AndroidDatabase extends RoomDatabase {

    public abstract NewsEntityDAO newsEntityDAO();
    public abstract UserEntityDAO usersEntityDAO();

    private static volatile AndroidDatabase INSTANCE;

    public static AndroidDatabase getAndroidDatabase(final Context context) {
        if(INSTANCE == null){
            synchronized (AndroidDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AndroidDatabase.class, "android_database")
                            .fallbackToDestructiveMigration() // !! nieprawidłowe rozwiązanie
                            .build();
                }
            }
        }
        return INSTANCE;
    }



}
