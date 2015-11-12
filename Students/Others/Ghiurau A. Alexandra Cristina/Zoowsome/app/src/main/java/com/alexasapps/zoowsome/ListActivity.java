package com.alexasapps.zoowsome;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import models.animals.Animal;
import repositories.AnimalRepository;
import services.factories.adapter.CustomAdapter;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);

        AnimalRepository animalRepo = new AnimalRepository(this);
        ArrayList<Animal> animalList = new ArrayList<Animal>();
        try {
            animalList = animalRepo.load();
        } catch (Exception e) {

        }
        ListAdapter adapter = new CustomAdapter(this, animalList);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String animal = parent.getItemAtPosition(position).getClass().getSimpleName();
                         Toast.makeText(getApplicationContext(), "Its name is " + animal.getClass().getName(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
}
