package com.example.roxana.zoo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.roxana.zoo.models.animals.Animal;

import java.util.ArrayList;

/**
 * Created by Roxana on 10/27/2015.
 */
public class CustomAdapter extends BaseAdapter {

    ArrayList<SingleRow> list;
    Context context;

    public CustomAdapter(Context context,ArrayList<Animal>animals){

        this.context=context;
        list = new ArrayList<SingleRow>();
        for(int i=0;i<animals.size();i++){

            String name = animals.get(i).getName();
            String animalType = animals.get(i).getAnimalType();
            int image = animals.get(i).getImage();
            list.add(new SingleRow(name,animalType,image));
        }

    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row =layoutInflater.inflate(R.layout.row_layout,viewGroup,false);

        SingleRow temp = list.get(position);

        TextView name = (TextView) row.findViewById(R.id.nameTextView);
        TextView typeOfAnimal = (TextView) row.findViewById(R.id.typeOfAnimalTextView);
        ImageView imageView = (ImageView) row.findViewById(R.id.imageView);

        name.setText(temp.name);
        typeOfAnimal.setText("Animal type:"+temp.animalType);
        imageView.setImageResource(temp.image);
        return row;
    }

    class SingleRow{
        String name;
        String animalType;
        int image;

        SingleRow(String name, String animalType,int image){
            this.name=name;
            this.animalType=animalType;
            this.image=image;
        }
    }
}
