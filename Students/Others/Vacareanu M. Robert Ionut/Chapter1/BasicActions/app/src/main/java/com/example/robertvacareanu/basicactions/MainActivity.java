package com.example.robertvacareanu.basicactions;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void changeColorOfButton(Button button) {
        Random random = new Random();
        button.setBackgroundColor(Color.argb(255, random.nextInt(255), random.nextInt(255), random.nextInt(255)));
    }

    public void changeBackgroundColor(View view) {
        Random random = new Random();
        view.setBackgroundColor(Color.argb(255, random.nextInt(255), random.nextInt(255), random.nextInt(255)));
    }

    static int firstButtonClicked, secondButtonClicked;

    public void buttonOnClick(View view) {
        Button buttonClicked;
        RadioGroup rg = (RadioGroup) findViewById(R.id.radio_group);
        switch (view.getId()) {
            case R.id.button_push_me:
                firstButtonClicked++;
                buttonClicked = (Button) findViewById(R.id.button_push_me);
                buttonClicked.setText("I was clicked " + firstButtonClicked + " times");
                if (rg.getCheckedRadioButtonId() == R.id.radio_button_1) {
                    changeColorOfButton(buttonClicked);
                } else if (rg.getCheckedRadioButtonId() == R.id.radio_button_2) {
                    changeBackgroundColor(findViewById(R.id.main_layout));
                } else {
                    Toast.makeText(this, "Push Me had been clicked", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.button_click_me:
                secondButtonClicked++;
                buttonClicked = (Button) findViewById(R.id.button_click_me);
                buttonClicked.setText("I was clicked " + secondButtonClicked + " times");
                if (rg.getCheckedRadioButtonId() == R.id.radio_button_1) {
                    changeColorOfButton(buttonClicked);
                } else if (rg.getCheckedRadioButtonId() == R.id.radio_button_2) {
                    changeBackgroundColor(findViewById(R.id.main_layout));
                } else {
                    Toast.makeText(this, "Click Me had been clicked", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                break;
        }
    }

}
