package com.example.socialmanager.Database;
//AFTER THIS FILE THE DB IS READY
//Todo: HOW IS IT READY? HELPPPPPPPPPPPPP
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.socialmanager.Models.ImBitMap;
import com.example.socialmanager.Models.Posts;

@Database(entities = Posts.class, version = 2, exportSchema = false)
@TypeConverters({ImBitMap.class})
public abstract class RoomDB extends RoomDatabase {
    private static RoomDB database;
    private static String DATABASE_NAME = "PostApp";

    //Method to basically get an instance of the database
    public synchronized static RoomDB getInstance(Context context){
        if (database == null){
            database = Room.databaseBuilder(context.getApplicationContext(),
                    RoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public abstract MainDAO mainDAO();
}
