package com.example.andreea.zoowsome;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import javasmmr.zoowsome.models.animals.Animal;
import javasmmr.zoowsome.models.animals.Cockroach;
import javasmmr.zoowsome.models.animals.Flamingo;
import javasmmr.zoowsome.repositories.AnimalRepository;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


         AnimalRepository animalRepo = new AnimalRepository();
         ArrayList<Animal> animals = null;
        /*try {
            animals=animalRepo.load();

        } catch (Exception e) {
            Toast.makeText(ListActivity.this, "", Toast.LENGTH_SHORT).show();
            e.printStackTrace();

        }
        finally {*/
            animals.add(new Cockroach());
            animals.add(new Flamingo());
            ListAdapter myAdapter = new CostumAdapter(this, animals);
            ListView myListView = (ListView) findViewById(R.id.myListView);
            myListView.setAdapter(myAdapter);


          /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
*/
    }

}
