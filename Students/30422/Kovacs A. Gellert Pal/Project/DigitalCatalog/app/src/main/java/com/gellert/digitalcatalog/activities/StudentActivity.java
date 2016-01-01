package com.gellert.digitalcatalog.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gellert.digitalcatalog.R;
import com.gellert.digitalcatalog.database.CatalogDatabaseHelper;
import com.gellert.digitalcatalog.models.Student;

public class StudentActivity extends AppCompatActivity {

    CatalogDatabaseHelper catalogDB;
    TextView txtNameAndClass;
    ListView markList;
    Student student;
    int personID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        catalogDB = CatalogDatabaseHelper.getInstance(StudentActivity.this);
        personID = Integer.valueOf(getIntent().getStringExtra("personID"));
        student = catalogDB.getStudentInfoFromDatabase(personID);

        txtNameAndClass = (TextView)findViewById(R.id.txtNameAndClass);
        txtNameAndClass.setText("Signed in as " + student.getName() + " - " + student.getClassName());

        if (student.getMarks() != null) {
            ListAdapter adapter = new MarkListAdapter(this, student.getMarks(), true);
            markList = (ListView) findViewById(R.id.listView);
            markList.setAdapter(adapter);
        }
    }
}
