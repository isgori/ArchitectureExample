package com.example.architectureexample;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Note.class},version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instances;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context){
        if (instances != null) {
            instances = Room.databaseBuilder(context.getApplicationContext()
            ,NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instances;
    }

}
