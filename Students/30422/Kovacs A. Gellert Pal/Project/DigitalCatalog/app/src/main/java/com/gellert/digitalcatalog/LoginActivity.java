package com.gellert.digitalcatalog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button btnSignIn;
    EditText txtUsername;
    EditText txtPassword;
    CatalogDatabaseHelper catalogDB;

    int personID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        catalogDB = new CatalogDatabaseHelper(this);

        btnSignIn = (Button)findViewById(R.id. btnSignIn);
        txtUsername = (EditText)findViewById(R.id. txtUsername);
        txtPassword = (EditText)findViewById(R.id.txtPassword);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();

                if(catalogDB.authenticateUser(username, password)) {
                    personID = catalogDB.getPersonID(username);
                    Toast.makeText(LoginActivity.this, "Success: " + personID, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(LoginActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
