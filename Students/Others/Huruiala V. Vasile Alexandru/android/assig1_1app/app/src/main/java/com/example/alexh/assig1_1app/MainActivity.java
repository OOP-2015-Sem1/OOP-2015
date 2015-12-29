package com.example.alexh.assig1_1app;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int pushed = 0;
    private int clicked = 0;

    private static int[] RANDOM_COLORS_RESOURCE = {Color.RED, Color.GREEN, Color.BLUE};
    private static int[] RANDOM_TEXT_COLORS_RESOURCE = {Color.MAGENTA, Color.CYAN, Color.YELLOW};

    // The digits are added twice to match the two sets of letters
    private static String CHARACTER_BANK = "abcdefghijkmnopqrstuvwxyz" + "abcdefghijkmnopqrstuvwxyz".toUpperCase() + "01234567890123456789";

    private int getRandomColor() {
        return RANDOM_COLORS_RESOURCE[(int) (Math.random() * RANDOM_COLORS_RESOURCE.length)];
    }

    private int getRandomTextColor() {
        return RANDOM_TEXT_COLORS_RESOURCE[(int) (Math.random() * RANDOM_TEXT_COLORS_RESOURCE.length)];
    }

    private String randomString(int lenght) {
        StringBuilder string = new StringBuilder(lenght);

        for (int i = 0; i < lenght; i++) {
            string.append(CHARACTER_BANK.charAt((int) (Math.random() * CHARACTER_BANK.length())));
        }

        return string.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textViewPushed = (TextView) findViewById(R.id.textViewPushed);

        Button pushMeButton = (Button) findViewById(R.id.pushMeButton);
        pushMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushed++;

                Button b = (Button) v;
                b.setBackgroundColor(getRandomColor());
                b.setTextColor(getRandomTextColor());

                Toast toast = Toast.makeText(getApplicationContext(), "Pushed!", Toast.LENGTH_SHORT);
                toast.show();

                textViewPushed.setText(String.format("Pushed %d times", pushed));
            }
        });

        pushMeButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                textViewPushed.setTextColor(getRandomTextColor());
                return true;
            }
        });

        final TextView textViewClicked = (TextView) findViewById(R.id.textViewClicked);

        Button clickMeButton = (Button) findViewById(R.id.clickMeButton);
        clickMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked++;

                Button b = (Button) v;
                b.setBackgroundColor(getRandomColor());
                b.setTextColor(getRandomTextColor());

                Toast toast = Toast.makeText(getApplicationContext(), "Clicked!", Toast.LENGTH_SHORT);
                toast.show();

                textViewClicked.setText(String.format("Clicked %d times", clicked));
            }
        });

        clickMeButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                textViewClicked.setTextColor(getRandomTextColor());
                return true;
            }
        });

        final TextView centerTextView = (TextView) findViewById(R.id.textViewMessage);

        Button centerButton = (Button) findViewById(R.id.centerButton);
        centerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                centerTextView.setText(randomString(8));
            }
        });

        centerButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                centerTextView.setTextColor(getRandomColor());
                return true;
            }
        });
    }
}
