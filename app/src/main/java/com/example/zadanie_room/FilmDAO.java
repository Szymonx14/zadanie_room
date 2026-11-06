package com.example.zadanie_room;
import androidx.room3.Delete;
import androidx.room3.Insert;
import androidx.room3.Query;
import androidx.room3.Update;
import java.util.List;

public interface FilmDAO {
    @Insert
    public void wstawFilmDoBazy(Film film);

    @Insert
    public void wstawFilmDoBazy(Film ... filmy);

    @Delete
    public void usunZBazy(Film film);

    @Update
    public void aktualizujFilm(Film film);

    @Query("SELECT * from filmy")
    public List<Film> zwrocWszystkieFilmy();

    @Query("SELECT nazwa from filmy where ileMinut = :ile")
    public List<String> zwrocNazwyFilmowDlaLiczbyMinut(int ile);


}
