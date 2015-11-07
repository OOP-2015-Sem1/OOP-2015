package com.gellert.zoowsome.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gellert.zoowsome.R;
import com.gellert.zoowsome.models.animals.Animal;

import java.util.ArrayList;

/**
 * Created by Gellért on 2015. 10. 31..
 */
public class CustomListAdapter extends ArrayAdapter<Animal> {
    public CustomListAdapter(Context context, ArrayList<Animal> animals) {
        super(context, R.layout.animal_row, animals);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.animal_row, parent, false);

        Animal singleAnimal = getItem(position);
        TextView txtName = (TextView) customView.findViewById(R.id.txtName);
        TextView txtType = (TextView) customView.findViewById(R.id.txtType);
        ImageView image = (ImageView) customView.findViewById(R.id.imageView);
        int imageId;
        switch (singleAnimal.getClass().getSimpleName()) {
            case "Butterfly": imageId = R.drawable.butterfly; break;
            case "Cockroach": imageId = R.drawable.cockroach; break;
            case "Cow": imageId = R.drawable.cow; break;
            case "Crocodile": imageId = R.drawable.crocodile; break;
            case "Dolphin": imageId = R.drawable.dolphin; break;
            case "Eagle": imageId = R.drawable.eagle; break;
            case "Lizard": imageId = R.drawable.lizard; break;
            case "Mockingjay": imageId = R.drawable.mockingjay; break;
            case "Pigeon": imageId = R.drawable.pigeon; break;
            case "Piranha": imageId = R.drawable.piranha; break;
            case "Sardine": imageId = R.drawable.sardine; break;
            case "Scarabeus": imageId = R.drawable.scarabeus; break;
            case "Tiger": imageId = R.drawable.tiger; break;
            case "Turtle": imageId = R.drawable.turtle; break;
            case "Wolf": imageId = R.drawable.wolf; break;
            default: imageId = R.drawable.image;
        }

        txtName.setText(singleAnimal.getName());
        txtType.setText(singleAnimal.getClass().getSimpleName());
        image.setImageResource(imageId);

        return customView;
    }
}
