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

    Button btnDodaj, btnAktualizuj;
    EditText etMin, etNazwa, etRezyser;
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
        btnAktualizuj = findViewById(R.id.buttonAktualizacja);
        etNazwa = findViewById(R.id.etName);
        etMin = findViewById(R.id.etMin);
        swAnimacja = findViewById(R.id.switch1);
        spNazwaKategori = findViewById(R.id.spCategoryName);
        etRezyser = findViewById(R.id.etDirector);
        listView = findViewById(R.id.listView);

        filmList = filmDatabase.zwrocGryDao().zwrocWszystkieFilmy();
        arrayAdapter = new ArrayAdapter<>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                filmList
        );
        listView.setAdapter(arrayAdapter);


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


                odswiezListe(filmDatabase);
                wyczyscPola();
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCardToEdit = filmList.get(i);

                etNazwa.setText(selectedCardToEdit.nazwa);
                etMin.setText(String.valueOf(selectedCardToEdit.ileMinut));
                swAnimacja.setChecked(selectedCardToEdit.czyAnimacja);
                etRezyser.setText(selectedCardToEdit.rezyser);


                ArrayAdapter spinnerAdapter = (ArrayAdapter) spNazwaKategori.getAdapter();
                int pozycja = spinnerAdapter.getPosition(selectedCardToEdit.kategoriaNazwa);
                spNazwaKategori.setSelection(pozycja);


                btnAktualizuj.setVisibility(View.VISIBLE);
            }
        });


        btnAktualizuj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedCardToEdit != null) {

                    selectedCardToEdit.nazwa = etNazwa.getText().toString();
                    selectedCardToEdit.ileMinut = Integer.parseInt(etMin.getText().toString());
                    selectedCardToEdit.kategoriaNazwa = spNazwaKategori.getSelectedItem().toString();
                    selectedCardToEdit.czyAnimacja = swAnimacja.isChecked();
                    selectedCardToEdit.rezyser = etRezyser.getText().toString();


                    filmDatabase.zwrocGryDao().aktualizujFilm(selectedCardToEdit);


                    odswiezListe(filmDatabase);
                    wyczyscPola();
                    selectedCardToEdit = null;
                    btnAktualizuj.setVisibility(View.INVISIBLE);
                }
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                filmDatabase.zwrocGryDao().usunZBazy(filmList.get(i));
                filmList.remove(i);
                arrayAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    private void odswiezListe(FilmDatabase db) {
        filmList.clear();
        filmList.addAll(db.zwrocGryDao().zwrocWszystkieFilmy());
        arrayAdapter.notifyDataSetChanged();
    }

    private void wyczyscPola() {
        etNazwa.setText("");
        etMin.setText("");
        etRezyser.setText("");
        swAnimacja.setChecked(false);
        spNazwaKategori.setSelection(0);
    }
}
