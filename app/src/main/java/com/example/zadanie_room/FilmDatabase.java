package com.example.zadanie_room;

import androidx.room3.Database;
import androidx.room3.Database;
import androidx.room3.Room;
import androidx.room3.RoomDatabase;

@Database(entities = {Film.class},version = 1, exportSchema = false)
public abstract class FilmDatabase extends RoomDatabase {
    public abstract FilmDAO zwrocGryDao();
}
