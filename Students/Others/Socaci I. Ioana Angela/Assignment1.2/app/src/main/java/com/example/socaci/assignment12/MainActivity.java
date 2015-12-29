package com.example.socaci.assignment12;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set layout
        final RelativeLayout myLayout = new RelativeLayout(this);
        myLayout.setBackgroundColor(Color.MAGENTA);

        //create button
        Button button = new Button(this);
        button.setText("Change background color");

        //create a field where to write(EditText)

        EditText username = new EditText(this);

        //set Id for button and username

        button.setId(1);
        username.setId(2);

        //modify the field dimensions(set the width of the field)
        //we have to convert DIP(Device Independent Pixels) into pixels

        Resources r = getResources();

        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, r.getDisplayMetrics());

        username.setWidth(px);

        //edit layout

        RelativeLayout.LayoutParams usernameDetails = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        RelativeLayout.LayoutParams buttonDetails = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        usernameDetails.addRule(RelativeLayout.ABOVE, button.getId());
        usernameDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);


        buttonDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);
        buttonDetails.addRule(RelativeLayout.CENTER_VERTICAL);

        myLayout.addView(button, buttonDetails);
        myLayout.addView(username, usernameDetails);
        setContentView(myLayout);

        //event handler for the button

        button.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                            myLayout.setBackgroundColor(Color.BLUE);
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
