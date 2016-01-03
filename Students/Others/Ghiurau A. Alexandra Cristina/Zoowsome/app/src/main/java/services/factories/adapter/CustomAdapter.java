package services.factories.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexasapps.zoowsome.R;

import java.util.ArrayList;

import models.animals.Animal;


public class CustomAdapter extends ArrayAdapter<Animal> {

    public CustomAdapter(Context context, ArrayList<Animal> animals) {
        super(context, R.layout.animal_list_layout, animals);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.animal_list_layout, parent, false);

        Animal animal = getItem(position);

        /*
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.animal_list_layout, parent, false);
        }*/

        TextView animalSpecies = (TextView) customView.findViewById(R.id.animalSpecies);
        TextView animalType = (TextView) customView.findViewById(R.id.animalType);
        TextView animalName = (TextView) customView.findViewById(R.id.animalName);
        ImageView image = (ImageView) customView.findViewById(R.id.imageView);


        //For each animal
        //animalSpecies.setText(animal.getClass().getClassLoader()...);
        animalType.setText(animal.getClass().getSimpleName());
        animalName.setText(animal.getName());

        image.setImageResource(animal.getImage());

        return customView;
    }
}
