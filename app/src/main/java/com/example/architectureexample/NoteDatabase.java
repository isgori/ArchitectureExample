package com.example.architectureexample;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Note.class},version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instances;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context){
        if (instances != null) {
            instances = Room.databaseBuilder(context.getApplicationContext()
            ,NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallbakc)
                    .build();
        }

        return instances;
    }

    private static RoomDatabase.Callback roomCallbakc = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instances).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{
        private  NoteDao noteDao;

        private PopulateDbAsyncTask(NoteDatabase db){
            this.noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title 1","Description 1",1));
            noteDao.insert(new Note("Title 2","Description 2",2));
            noteDao.insert(new Note("Title 3","Description 3",3));

            return null;
        }
    }

}
