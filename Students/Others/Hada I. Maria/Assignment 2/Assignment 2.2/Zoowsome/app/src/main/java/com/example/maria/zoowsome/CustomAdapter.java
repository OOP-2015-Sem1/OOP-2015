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

        String theAnimal = animal.getClass().getSimpleName();
        switch (theAnimal) {
            case "Butterfly":
                myText.setText(theAnimal);
                nameText.setText(animal.getName());
                myImage.setImageResource(R.drawable.butterfly);
                break;

            case "Bee":
                myText.setText(theAnimal);
                nameText.setText(animal.getName());
                myImage.setImageResource(R.drawable.bee);
                break;

            case "Beetle":
                myText.setText(theAnimal);
                nameText.setText(animal.getName());
                myImage.setImageResource(R.drawable.beetle);
                break;

            case "Dolphin":
                myText.setText(theAnimal);
                nameText.setText(animal.getName());
                myImage.setImageResource(R.drawable.dolphin);
                break;

            case "Jellyfish":
                myText.setText(theAnimal);
                nameText.setText(animal.getName());
                myImage.setImageResource(R.drawable.jellyfish);
                break;

            case "Swordfish":
                myText.setText(theAnimal);
                nameText.setText(animal.getName());
                myImage.setImageResource(R.drawable.swordfish);
                break;

            case "Flamingo":
                myText.setText(theAnimal);
                nameText.setText(animal.getName());
                myImage.setImageResource(R.drawable.flamingo);
                break;

            case "Hummingbird":
                myText.setText(theAnimal);
                nameText.setText(animal.getName());
                myImage.setImageResource(R.drawable.hummingbird);
                break;

            case "Owl":
                myText.setText(theAnimal);
                nameText.setText(animal.getName());
                myImage.setImageResource(R.drawable.owl);
                break;

            case "Cow":
                myText.setText(theAnimal);
                nameText.setText(animal.getName());
                myImage.setImageResource(R.drawable.cow);
                break;

            case "Monkey":
                myText.setText(theAnimal);
                nameText.setText(animal.getName());
                myImage.setImageResource(R.drawable.monkey);
                break;

            case "Tiger":
                myText.setText(theAnimal);
                nameText.setText(animal.getName());
                myImage.setImageResource(R.drawable.tiger);
                break;

            case "Iguana":
                myText.setText(theAnimal);
                nameText.setText(animal.getName());
                myImage.setImageResource(R.drawable.iguana);
                break;

            case "Turtle":
                myText.setText(theAnimal);
                nameText.setText(animal.getName());
                myImage.setImageResource(R.drawable.turtle);
                break;

            case "Viper":
                myText.setText(theAnimal);
                nameText.setText(animal.getName());
                myImage.setImageResource(R.drawable.viper);
                break;

            default:
                break;
        }
        return customView;
    }
}
