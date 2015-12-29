package com.gellert.digitalcatalog.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gellert.digitalcatalog.R;
import com.gellert.digitalcatalog.models.Mark;

import java.util.ArrayList;

/**
 * Created by Gellért on 2015. 12. 29..
 */
public class MarkListAdapter extends ArrayAdapter<Mark>{
    public MarkListAdapter(Context context, ArrayList<Mark> marks) {
        super(context, R.layout.mark_row, marks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.mark_row, parent, false);

        Mark singleMark = getItem(position);
        TextView txtSubject = (TextView) customView.findViewById(R.id.txtSubject);
        TextView txtMidterm = (TextView) customView.findViewById(R.id.txtMidterm);
        TextView txtMark = (TextView) customView.findViewById(R.id.txtMark);

        txtSubject.setText(singleMark.getSubject());
        txtMidterm.setText(String.valueOf(singleMark.isMidterm()));
        txtMark.setText(String.valueOf(singleMark.getMark()));

        return customView;
    }
}
