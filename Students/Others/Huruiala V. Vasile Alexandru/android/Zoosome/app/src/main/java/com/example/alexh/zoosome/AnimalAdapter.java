package com.example.alexh.zoosome;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alexh.zoosome.models.animals.Animal;
import com.example.alexh.zoosome.services.factories.Constants;

public class AnimalAdapter extends ArrayAdapter<Animal> {
    private Context context;

    public AnimalAdapter(Context context, Animal[] animals) {
        super(context, R.layout.animal_element, animals);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.animal_element, parent, false);

        Animal animal = getItem(position);

        ImageView animalImage = (ImageView) view.findViewById(R.id.animalImage);
        TextView nameTextView = (TextView) view.findViewById(R.id.animalName);
        TextView classTextView = (TextView) view.findViewById(R.id.animalClass);
        TextView speciesTextView = (TextView) view.findViewById(R.id.animalSpecies);

        /*
        Resources res = this.context.getResources();
        String drawableName = Constants.Animals.nameOfSpecies(animal) + "0";
        int resID = res.getIdentifier("bear0", "drawable", this.context.getPackageName());
        animalImage.setImageResource(resID);
        */
        animalImage.setImageResource(R.drawable.siegeonager1_final);

        nameTextView.setText(animal.getName());
        classTextView.setText(String.format("Class: %s", Constants.Animals.nameOfClass(animal)));
        speciesTextView.setText(String.format("Species: %s", Constants.Animals.nameOfSpecies(animal)));

        return view;
    }
}
