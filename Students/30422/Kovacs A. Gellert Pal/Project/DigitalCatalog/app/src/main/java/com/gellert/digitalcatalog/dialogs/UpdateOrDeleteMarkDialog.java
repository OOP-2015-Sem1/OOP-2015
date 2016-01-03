package com.gellert.digitalcatalog.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.gellert.digitalcatalog.R;
import com.gellert.digitalcatalog.database.CatalogDatabaseHelper;
import com.gellert.digitalcatalog.activities.ManageMarksActivity;

/**
 * Created by Gellért on 2016. 01. 01..
 */
public class UpdateOrDeleteMarkDialog extends Dialog {

    private CatalogDatabaseHelper catalogDB;

    public UpdateOrDeleteMarkDialog(final Context context,final int markID, final int personID,final int subjectID, int oldMark, boolean oldMidterm) {
        super(context);
        this.setContentView(R.layout.dialog_update_delete_mark);

        final EditText editTextMarkUpdate = (EditText)findViewById(R.id.editTextMarkUpdate);
        editTextMarkUpdate.setText(String.valueOf(oldMark));

        final CheckBox checkBoxMidtermUpdate = (CheckBox)findViewById(R.id.checkkBoxMidtermUpdate);
        checkBoxMidtermUpdate.setChecked(oldMidterm);

        Button btnUpdateMarkDialog = (Button)findViewById(R.id.btnUpdateMarkDialog);
        catalogDB = CatalogDatabaseHelper.getInstance(context);

        btnUpdateMarkDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mark = Integer.valueOf(editTextMarkUpdate.getText().toString());
                boolean midterm = checkBoxMidtermUpdate.isChecked();
                catalogDB.updateMark(markID, personID, subjectID, mark, midterm);
                ((ManageMarksActivity)context).refreshMarkListContent();
                (UpdateOrDeleteMarkDialog.this).dismiss();
            }
        });

        Button btnDeleteMarkDialog = (Button)findViewById(R.id.btnDeleteMarkDialog);
        btnDeleteMarkDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catalogDB.deleteMark(markID);
                ((ManageMarksActivity)context).refreshMarkListContent();
                (UpdateOrDeleteMarkDialog.this).dismiss();
            }
        });
    }
}
