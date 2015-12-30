package com.gellert.digitalcatalog.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Gellért on 2015. 12. 29..
 */
public class Alerts {
    private Context context;
    private String chosenClass;

    public Alerts(Context context) {
        this.context = context;
    }

    public void showClassChoiceDialog(final ArrayList<String> classNames) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.select_dialog_item);
        adapter.addAll(classNames);

        final AlertDialog.Builder builderDouble = new AlertDialog.Builder(context);
        builderDouble.setTitle("Select the second player: ");
        builderDouble.setAdapter(
                adapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        chosenClass = classNames.get(which);

                    }
                });
    }
}
