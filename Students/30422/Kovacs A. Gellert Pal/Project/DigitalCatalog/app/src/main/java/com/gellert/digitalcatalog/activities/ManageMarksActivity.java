package com.gellert.digitalcatalog.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gellert.digitalcatalog.R;
import com.gellert.digitalcatalog.database.CatalogDatabaseHelper;
import com.gellert.digitalcatalog.dialogs.AddMarkDialog;
import com.gellert.digitalcatalog.dialogs.UpdateOrDeleteMarkDialog;
import com.gellert.digitalcatalog.models.Student;

public class ManageMarksActivity extends AppCompatActivity {

    CatalogDatabaseHelper catalogDB;
    TextView txtStudentNameAndSubject;
    ListView markList;
    Button btnAddMark;
    Student student;
    int personID;
    int subjectID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_marks);

        catalogDB = CatalogDatabaseHelper.getInstance(ManageMarksActivity.this);
        personID = Integer.valueOf(getIntent().getStringExtra("personID"));
        String subject = getIntent().getStringExtra("subject");
        subjectID = catalogDB.getSubjectID(subject);
        student = catalogDB.getStudentInfoFromDatabaseForSubject(personID, subjectID);

        txtStudentNameAndSubject = (TextView)findViewById(R.id.txtStudentNameAnDSubject);
        txtStudentNameAndSubject.setText(subject + " marks of " + student.getName());

        markList = (ListView)findViewById(R.id.markList);
        if (student.getMarks() != null) {
            refreshMarkListContent();
        }

        btnAddMark = (Button)findViewById(R.id.btnAddMark);
        btnAddMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddMarkDialog addMarkDialog = new AddMarkDialog(ManageMarksActivity.this, personID,subjectID);
                addMarkDialog.show();
            }
        });

        markList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int markID = student.getMarks().get(position).getMarkID();
                int oldMark = student.getMarks().get(position).getMark();
                boolean oldMidterm = student.getMarks().get(position).isMidterm();
                UpdateOrDeleteMarkDialog updateOrDeleteMarkDialog = new UpdateOrDeleteMarkDialog(ManageMarksActivity.this, markID, personID, subjectID, oldMark, oldMidterm);
                updateOrDeleteMarkDialog.show();
            }
        });
    }

    public void refreshMarkListContent() {
        student.setMarks(catalogDB.getStudentMarksForSubject(personID, subjectID));
        if (student.getMarks() != null) {
            ListAdapter markListAdapter = new MarkListAdapter(this, student.getMarks(), false);
            markList.setAdapter(markListAdapter);
        }
        else {
            markList.setAdapter(null);
        }
    }
}
