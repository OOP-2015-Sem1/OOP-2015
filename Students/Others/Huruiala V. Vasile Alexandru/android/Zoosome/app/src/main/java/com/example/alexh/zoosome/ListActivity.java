package com.example.alexh.zoosome;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.alexh.zoosome.models.animals.Animal;
import com.example.alexh.zoosome.models.animals.Bear;
import com.example.alexh.zoosome.models.animals.Dove;
import com.example.alexh.zoosome.repositories.AnimalRepository;
import com.example.alexh.zoosome.repositories.ZooData;
import com.example.alexh.zoosome.services.factories.animals.BigFactory;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BigFactory bigFactory = new BigFactory();
        Animal[] a = bigFactory.generateRandomAnimalAmount(50);

        AnimalRepository animalRepository = new AnimalRepository(getApplicationContext());
        ArrayList<Animal> animalArrayList = null;
        try {
            animalArrayList = animalRepository.load();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("De ce nu merge?", "FILE NOT FOUND. PLEASE TRY AGAIN LATER.");
        }

        ZooData.setAnimalList(animalArrayList);
        ZooData.setAnimalList(a); // Overrides the line above, both are functional separately

        ListAdapter listAdapter = new AnimalAdapter(this, ZooData.getArrayAnimalList());
        ListView listView = (ListView) findViewById(R.id.animalList);
        listView.setAdapter(listAdapter);
    }
}
