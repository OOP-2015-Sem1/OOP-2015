package com.example.roxana.zoo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roxana.zoo.models.animals.Animal;
import com.example.roxana.zoo.repositories.AnimalRepository;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Roxana on 10/25/2015.
 */
public class MyListActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);//links the activity to its xml file


        AnimalRepository repo = new AnimalRepository(this);
        ArrayList<Animal> loadedAnimals = null;
        try {
            loadedAnimals = repo.load();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new CustomAdapter(this, loadedAnimals));

        //on a long click, add the extra details
        listView.setOnItemClickListener(new ItemClickListener(loadedAnimals));
    }

    class ItemClickListener implements AdapterView.OnItemClickListener {

        ArrayList<Animal> animals;

        ItemClickListener(ArrayList<Animal> animals) {
            this.animals = animals;
        }
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            TextView detailsTextView = (TextView) view.findViewById(R.id.detailsTextView);

            String details = (String) detailsTextView.getText();
            if(details.compareTo("")==0) {
                detailsTextView.setText("Extra details\n" + animals.get(position).toString());
                Toast.makeText(MyListActivity.this, "Click to hide", Toast.LENGTH_SHORT).show();
            }
            else{
                detailsTextView.setText("");
            }
        }
    }

    ;
}
