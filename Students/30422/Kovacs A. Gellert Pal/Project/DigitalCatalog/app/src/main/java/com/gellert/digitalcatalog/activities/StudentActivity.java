package com.gellert.digitalcatalog.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gellert.digitalcatalog.R;
import com.gellert.digitalcatalog.models.Mark;
import com.gellert.digitalcatalog.models.Student;

import java.util.ArrayList;

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

        catalogDB = new CatalogDatabaseHelper(this);
        personID = Integer.valueOf(getIntent().getStringExtra("personID"));
        student = getStudentInfoFromDatabase(personID);

        txtNameAndClass = (TextView)findViewById(R.id.txtNameAndClass);
        txtNameAndClass.setText("Signed in as " + student.getName() + " - " + student.getClassName());

        ListAdapter adapter = new MarkListAdapter(this, student.getMarks());
        markList = (ListView) findViewById(R.id.listView);
        markList.setAdapter(adapter);
    }

    private Student getStudentInfoFromDatabase(int personID) {
        String name = catalogDB.getPersonName(personID);
        String className = catalogDB.getStudentClassName(personID);
        ArrayList<Mark> marks = catalogDB.getStudentMarks(personID);

        return new Student(personID, name, className, marks);
    }
}
