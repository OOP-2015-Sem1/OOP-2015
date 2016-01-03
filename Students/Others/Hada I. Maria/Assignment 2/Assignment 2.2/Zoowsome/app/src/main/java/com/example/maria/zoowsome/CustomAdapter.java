package com.example.maria.zoowsome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maria.zoowsome.models.animals.Animal;

import java.util.ArrayList;

class CustomAdapter extends ArrayAdapter<Animal> {

    public CustomAdapter(Context context, ArrayList<Animal> animals) {

        super(context, R.layout.custom_row, animals);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View customView = layoutInflater.inflate(R.layout.custom_row, parent, false);

        Animal animal = getItem(position);
        TextView myText = (TextView) customView.findViewById(R.id.rowText);
        TextView nameText = (TextView) customView.findViewById(R.id.nameText);
        ImageView myImage = (ImageView) customView.findViewById(R.id.rowImage);

        myText.setText(animal.getClass().getSimpleName());
        nameText.setText(animal.getName());
        myImage.setImageResource(animal.getImage());

        return customView;
    }
}
