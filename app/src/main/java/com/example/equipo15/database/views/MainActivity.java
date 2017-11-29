package com.example.equipo15.database.views;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.equipo15.database.R;
import com.example.equipo15.database.adapters.PersonasAdapter;
import com.example.equipo15.database.helpers.SqliteHelper;
import com.example.equipo15.database.models.Persona;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerViewPersonas;
    PersonasAdapter personasAdapter;
    LinearLayoutManager linearLayoutManager;
    List<Persona> listaPersonas = new ArrayList<>();
    SqliteHelper sqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerViewPersonas = (RecyclerView) findViewById(R.id.personasRecylerView);
        recyclerViewPersonas.setLayoutManager(linearLayoutManager);

        sqliteHelper  = new SqliteHelper(this, "db_persons", null, 1);

        listPersons();

    }

    public void goToRegister(View view){
        Intent i = new Intent (this, RegisterActivity.class);
        startActivity(i);
    }

    public void listPersons() {
        SQLiteDatabase db = sqliteHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("select id,name,lastname,age,phone from persons", null);

        while (cursor.moveToNext()){
            listaPersonas.add(new Persona(cursor.getInt(0), cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getString(4)));

        }
        cursor.close();

        if(listaPersonas.size() > 0){
            processData();
        }
        else {
            Toast.makeText(this,"No hay elementeso para mostrar", Toast.LENGTH_SHORT).show();
        }
    }

    public void processData() {
        personasAdapter = new PersonasAdapter(listaPersonas, getApplicationContext());
        recyclerViewPersonas.setAdapter(personasAdapter);

    }
}
