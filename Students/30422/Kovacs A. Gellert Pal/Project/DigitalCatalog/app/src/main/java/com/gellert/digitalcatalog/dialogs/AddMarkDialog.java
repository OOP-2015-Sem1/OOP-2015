package com.gellert.digitalcatalog.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.gellert.digitalcatalog.R;
import com.gellert.digitalcatalog.database.CatalogDatabaseHelper;
import com.gellert.digitalcatalog.activities.ManageMarksActivity;

/**
 * Created by Gellért on 2015. 12. 31..
 */
public class AddMarkDialog extends Dialog {

    CatalogDatabaseHelper catalogDB;

    public AddMarkDialog(final Context context, final int personID, final int subjectID) {
        super(context);
        this.setContentView(R.layout.dialog_addmark);

        final EditText editTextMark = (EditText)findViewById(R.id.editTextMark);
        final CheckBox checkBoxMidterm = (CheckBox)findViewById(R.id.checkkBoxMidterm);
        Button btnAddMarkDialog = (Button)findViewById(R.id.btnAddMarkDialog);
        catalogDB = CatalogDatabaseHelper.getInstance(context);

        btnAddMarkDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextMark.getText().length() == 0) {
                    Toast.makeText(context, "Empty mark cannot be added", Toast.LENGTH_SHORT).show();
                    (AddMarkDialog.this).dismiss();
                }
                else {
                    int mark = Integer.valueOf(editTextMark.getText().toString());
                    boolean midterm = checkBoxMidterm.isChecked();
                    catalogDB.addMark(personID, subjectID, mark, midterm);
                    ((ManageMarksActivity)context).refreshMarkListContent();
                    (AddMarkDialog.this).dismiss();
                }
            }
        });
    }
}
