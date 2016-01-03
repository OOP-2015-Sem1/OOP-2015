package com.example.robertvacareanu.zoowsomeandroid1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import models.animals.Animal;
import services.factories.Constants;

public class CustomAdapter extends ArrayAdapter<String> {

    private Activity activity;
    private ArrayList<Animal> animals;

    public CustomAdapter(Activity activity, ArrayList list) {
        super(activity, R.layout.custom_list, list);
        this.activity = activity;
        animals = list;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        if (view == null) view = inflater.inflate(R.layout.custom_list, parent, false);

        TextView title = (TextView) view.findViewById(R.id.title_text_view);
        TextView description = (TextView) view.findViewById(R.id.description_text_view);
        ImageView image = (ImageView) view.findViewById(R.id.imageView);

        title.setText(animals.get(position).getName());
        description.setText(animals.get(position).getClass().getSimpleName());
        image.setImageResource(Constants.EntityImageLinker.entityImageLinker.get(animals.get(position).getClass().getSimpleName()));

        return view;
    }

}

