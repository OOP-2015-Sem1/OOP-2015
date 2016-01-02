package com.gellert.digitalcatalog.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gellert.digitalcatalog.R;
import com.gellert.digitalcatalog.database.CatalogDatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    private static final int USERTYPE_TEACHER = 1;
    private static final int USERTYPE_STUDENT = 2;

    Button btnSignIn;
    EditText txtUsername;
    EditText txtPassword;
    CatalogDatabaseHelper catalogDB;

    int personID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        catalogDB = CatalogDatabaseHelper.getInstance(LoginActivity.this);

        btnSignIn = (Button)findViewById(R.id. btnSignIn);
        txtUsername = (EditText)findViewById(R.id. txtUsername);
        txtPassword = (EditText)findViewById(R.id.txtPassword);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();
                txtUsername.setText("");
                txtPassword.setText("");

                if(catalogDB.authenticateUser(username, password)) {
                    personID = catalogDB.getPersonIDByUserName(username);
                    Toast.makeText(LoginActivity.this, "Welcome " + catalogDB.getPersonName(personID) + "!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(LoginActivity.this, "Username or password is incorrect!", Toast.LENGTH_SHORT).show();
                }

                int userType = catalogDB.getUserType(personID);
                if (userType == USERTYPE_TEACHER) {
                    Intent teacherActivityIntent = new Intent(LoginActivity.this, TeacherActivity.class);
                    teacherActivityIntent.putExtra("personID", String.valueOf(personID));
                    startActivity(teacherActivityIntent);
                }
                else if (userType == USERTYPE_STUDENT) {
                    Intent studentActivityIntent = new Intent(LoginActivity.this, StudentActivity.class);
                    studentActivityIntent.putExtra("personID", String.valueOf(personID));
                    startActivity(studentActivityIntent);
                }
                else {
                    Toast.makeText(LoginActivity.this, "Error while getting USERTYPE.", Toast.LENGTH_SHORT).show();
                }

                personID = 0;
            }
        });
    }
}
