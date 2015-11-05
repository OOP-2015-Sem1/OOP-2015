package com.example.andreea.zoowsome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import javasmmr.zoowsome.models.animals.Animal;

/**
 * Created by Andreea on 04.11.2015.
 */
public class CostumAdapter extends ArrayAdapter<Animal> {

    public CostumAdapter(Context context, ArrayList<Animal> animals) {
        super(context, R.layout.costum_row, animals);
    }
@Override
    public View getView(int position,View convertView,ViewGroup parent){
        LayoutInflater myInflater=LayoutInflater.from(getContext());
        View customView=myInflater.inflate(R.layout.costum_row, parent, false);

        Animal singleAnimal=getItem(position);
        TextView animalText=(TextView) customView.findViewById(R.id.animal);
        TextView animalName=(TextView) customView.findViewById(R.id.name);
        ImageView animalImage=(ImageView) customView.findViewById(R.id.imageID);
        animalText.setText(singleAnimal.getClass().getName());
        animalName.setText(singleAnimal.getName());
        switch (singleAnimal.getClass().getName()) {
            case "Albatross":
            animalImage.setImageResource(R.mipmap.albatross);
                break;
            case "Amphisbaenian":
            animalImage.setImageResource(R.mipmap.amphisbaenian);
                break;
            case "Butterfly":
            animalImage.setImageResource(R.mipmap.butterfly); break;
            case "Chameleon":
            animalImage.setImageResource(R.mipmap.chameleon); break;
            case "Cockroach":
            animalImage.setImageResource(R.mipmap.cockroach); break;
            case "Cow":
            animalImage.setImageResource(R.mipmap.cow); break;
            case "Flamingo":
            animalImage.setImageResource(R.mipmap.flamingo); break;
            case "Monkey":
            animalImage.setImageResource(R.mipmap.mokey); break;
            case "Owl":
            animalImage.setImageResource(R.mipmap.owl); break;
            case "Penguin":
            animalImage.setImageResource(R.mipmap.penguin); break;
            case "Salmon":
            animalImage.setImageResource(R.mipmap.salmon); break;
            case "SeaHorse":
            animalImage.setImageResource(R.mipmap.seahorse); break;
            case "Shark":
            animalImage.setImageResource(R.mipmap.shark); break;
            case "Spider":
            animalImage.setImageResource(R.mipmap.spider); break;
            case "Tiger":
            animalImage.setImageResource(R.mipmap.tiger); break;
            case "Tuataras":animalImage.setImageResource(R.mipmap.tuataras);
             break;
            default:animalImage.setImageResource(R.mipmap.tuataras);
                break;
        }


        return customView;
    }
}
