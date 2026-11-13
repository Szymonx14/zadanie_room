package com.example.zadanie_room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "filmy")
public class Film {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String nazwa;
    @ColumnInfo(name = "ileM")
    public int ileMinut;
    public String kategoriaNazwa;
    public boolean czyAnimacja;
    public String rezyser;

    public Film(String nazwa, int ileMinut, String kategoriaNazwa, boolean czyAnimacja, String rezyser) {
        this.nazwa = nazwa;
        this.ileMinut = ileMinut;
        this.kategoriaNazwa = kategoriaNazwa;
        this.czyAnimacja = czyAnimacja;
        this.rezyser = rezyser;
    }

    @Override
    public String toString() {
        return "Film:" + '\n' +
                "id: " + id +'\n' +
                "nazwa: " + nazwa + '\n' +
                "czas trwania w minutach: " + ileMinut + '\n' +
                "kategoria: " + kategoriaNazwa + '\n' +
                "czy animacja: " + czyAnimacja + '\n' +
                "rezyser: " + rezyser ;
    }
}
