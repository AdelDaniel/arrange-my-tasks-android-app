package com.example.arrangemytasks;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {


    private static AppDatabase myAppDatabase ;
    public static final String  databaseName = "TaskDatabase";
    public abstract TaskDao taskDao();

    public synchronized static AppDatabase getInstance(Context context){
        if(myAppDatabase == null){
            myAppDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, databaseName)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return myAppDatabase;
    }

    public static void destroyDatabase(){
        myAppDatabase = null;
    }
}
