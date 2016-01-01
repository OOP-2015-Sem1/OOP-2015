package com.example.roxanappop.chess.View;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.roxanappop.chess.Controller.Controller;
import com.example.roxanappop.chess.R;

public class MainActivity extends AppCompatActivity implements FragmentActivity_I {

    Controller controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add fragment play to the activity
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        PlayButtonFragment playButtonFragment = new PlayButtonFragment();
        fragmentTransaction.replace(R.id.fragment, playButtonFragment);
        fragmentTransaction.commit();
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
        } else if (id == R.id.exit_aplication) {
        finish();
        System.exit(0);
        return true;
    } else if (id == R.id.surrender) {

        controller.takeSurrenderActions();
        return true;
    }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Controller getControllerInstance() {

        controller = new Controller(this);
        return controller ;
    }
}
