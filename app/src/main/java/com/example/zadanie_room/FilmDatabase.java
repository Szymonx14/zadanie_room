package com.example.zadanie_room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Film.class},version = 1, exportSchema = false)
public abstract class FilmDatabase extends RoomDatabase {
    public abstract FilmDAO zwrocGryDao();
}
