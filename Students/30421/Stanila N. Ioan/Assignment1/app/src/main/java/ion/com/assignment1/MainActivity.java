package ion.com.assignment1;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Chronometer;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static int clicks;
    public static int pushes;
    private int scorev;

    private String[] commands = {
            "Puuuush!",
            "Ccclick!"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        clicks = 0;
        pushes = 0;
        scorev = 0;

        final RelativeLayout relLayout = (RelativeLayout) findViewById(R.id.relLayout);

        final Toast toastPush = Toast.makeText(getApplicationContext(), "You pushed", Toast.LENGTH_SHORT);
        final Toast toastClick = Toast.makeText(getApplicationContext(), "You clicked", Toast.LENGTH_SHORT);

        final TextView text = (TextView) findViewById(R.id.contentLargeTextThisIsATest);
        text.setText("Push or click\nStart at 00:03");

        final TextView score = (TextView) findViewById(R.id.textViewActualScore);

        final Chronometer chrono = (Chronometer) findViewById(R.id.chronometer);
        chrono.start();

        final Button buttonPushMe = (Button) findViewById(R.id.contentButtonPushMe);
        buttonPushMe.setEnabled(false);
        buttonPushMe.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                pushes++;
                ((Button) v).setText("You pushed " + String.valueOf(pushes) + " times");

                if (text.getText().equals(commands[0])) {
                    scorev += 1;
                    text.setText(getRandomCommand());
                } else {
                    scorev -= 2;
                }

                score.setText(String.valueOf(scorev));

                toastClick.cancel();
                toastPush.show();

                //change the theme
                ((Button) v).setBackgroundColor(getRandomColor());
                relLayout.setBackgroundColor(getRandomColor());
                text.setTextColor(getRandomColor());

                return true;
            }
        });

        final Button buttonClickMe = (Button) findViewById(R.id.contentButtonClickMe);
        buttonClickMe.setEnabled(false);
        buttonClickMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicks++;
                ((Button) v).setText("You clicked " + String.valueOf(clicks) + " times");

                if (text.getText().equals(commands[1])) {
                    scorev += 2;
                    text.setText(getRandomCommand());
                } else {
                    scorev -= 1;
                }

                score.setText(String.valueOf(scorev));

                toastPush.cancel();
                toastClick.show();

                //change the theme
                ((Button) v).setBackgroundColor(getRandomColor());
                ((Button) v).setBackgroundColor(getRandomColor());
                relLayout.setBackgroundColor(getRandomColor());
                text.setTextColor(getRandomColor());
            }
        });

        chrono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {

            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if (chronometer.getText().equals("00:03")) {
                    buttonClickMe.setEnabled(true);
                    buttonPushMe.setEnabled(true);
                    text.setText(getRandomCommand());
                } else if (chronometer.getText().equals("00:20")) {
                    text.setText("You lose :(");
                    buttonClickMe.setEnabled(false);
                    buttonPushMe.setEnabled(false);
                } else if (chronometer.getText().equals("00:25")) {
                    System.exit(0);
                }
            }
        });
    }

    public int getRandomColor() {
        Random rnd = new Random();
        int red = rnd.nextInt(256);
        int green = rnd.nextInt(256);
        int blue = rnd.nextInt(256);
        return Color.rgb(red, green, blue);
    }

    public String getRandomCommand() {
        Random r = new Random();
        return commands[r.nextInt(2)];
    }

}
