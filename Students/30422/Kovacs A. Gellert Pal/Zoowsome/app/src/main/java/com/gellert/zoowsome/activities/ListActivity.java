package com.gellert.zoowsome.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.gellert.zoowsome.R;
import com.gellert.zoowsome.models.animals.Animal;
import com.gellert.zoowsome.repositories.AnimalRepository;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        AnimalRepository animalRepo = new AnimalRepository();
        ArrayList<Animal> animalList= new ArrayList<Animal>();
        try {
            animalList = animalRepo.load(this);
        }
        catch (Exception e) {

        }
        ListAdapter adapter = new CustomListAdapter(this, animalList);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String animal = parent.getItemAtPosition(position).getClass().getSimpleName();
                        Toast.makeText(getApplicationContext(), "You clicked on a(n) " + animal, Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
}
