package com.example.zadanie_room;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btnDodaj;
    EditText etMin,etNazwa,etRezyser;
    Spinner spNazwaKategori;

    Switch swAnimacja;

    ListView listView;

    List<Film> filmList;
    ArrayAdapter<Film> arrayAdapter;
    Film selectedCardToEdit = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FilmDatabase filmDatabase = Room.databaseBuilder(
                getApplicationContext(), FilmDatabase.class, "film_db")
                .allowMainThreadQueries().build();


        btnDodaj = findViewById(R.id.btnSend);
        etNazwa = findViewById(R.id.etName);
        etMin = findViewById(R.id.etMin);
        swAnimacja = findViewById(R.id.switch1);
        spNazwaKategori = findViewById(R.id.spCategoryName);
        etRezyser = findViewById(R.id.etDirector);

        listView = findViewById(R.id.listView);

        btnDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Film film = new Film(
                        etNazwa.getText().toString(),
                        Integer.parseInt(etMin.getText().toString()),
                        spNazwaKategori.getSelectedItem().toString(),
                        swAnimacja.isChecked(),
                        etRezyser.getText().toString()

                );
                filmDatabase.zwrocGryDao().wstawFilmDoBazy(film);
            }
        });

        filmList = filmDatabase.zwrocGryDao().zwrocWszystkieFilmy();
        arrayAdapter = new ArrayAdapter<>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                filmList
        );
        listView.setAdapter(arrayAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                filmDatabase.zwrocGryDao().usunZBazy(filmList.get(i));
                filmList.remove(i);
                arrayAdapter.notifyDataSetChanged();

                return false;
            }
        });



    }
}