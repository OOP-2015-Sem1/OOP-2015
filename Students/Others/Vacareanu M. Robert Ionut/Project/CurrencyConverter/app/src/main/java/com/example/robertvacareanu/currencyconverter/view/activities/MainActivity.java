package com.example.robertvacareanu.currencyconverter.view.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.robertvacareanu.currencyconverter.R;
import com.example.robertvacareanu.currencyconverter.business.CurrencyConverter;
import com.example.robertvacareanu.currencyconverter.business.DataLoadingListener;
import com.example.robertvacareanu.currencyconverter.business.ResourceManager;
import com.example.robertvacareanu.currencyconverter.business.jsonparser.CurrencyJsonParser;
import com.example.robertvacareanu.currencyconverter.view.fragments.CurrencyFragment;
import com.example.robertvacareanu.currencyconverter.view.fragments.SplashScreenFragment;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements DataLoadingListener, CurrencyConverter {

    private Toolbar toolbar;
    private Spinner provider;
    private String url = providerUrlLinker.get(PROVIDER_ONE);
    private ResourceManager resourceManager = new ResourceManager(this);
    private SplashScreenFragment splashScreen;
    private CurrencyJsonParser parser = new CurrencyJsonParser();
    private CurrencyFragment currencyFragment;

    @Override
    public void onError() {
        provider = (Spinner) findViewById(R.id.source_spinner);
        provider.setVisibility(View.VISIBLE);
        toolbar.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction().remove(splashScreen).commit();
        getWindow().getDecorView().setBackgroundColor(Color.LTGRAY);
        if (currencyFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(currencyFragment).commit();
        }
    }

    @Override
    public void onSuccess(HashMap<String, Double> results) {
        getSupportFragmentManager().beginTransaction().remove(splashScreen).commit();
        provider = (Spinner) findViewById(R.id.source_spinner);
        provider.setVisibility(View.VISIBLE);
        toolbar.setVisibility(View.VISIBLE);
        currencyFragment = CurrencyFragment.newInstance(results);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, currencyFragment).commit();
        getWindow().getDecorView().setBackgroundColor(Color.LTGRAY);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "Loaded", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getWindow().getDecorView().setBackgroundColor(Color.WHITE);

        final ArrayList<String> providerSource = new ArrayList<>(providerUrlLinker.keySet());
        Spinner provider = (Spinner) findViewById(R.id.source_spinner);
        ArrayAdapter<String> providerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, providerSource);
        providerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        provider.setAdapter(providerAdapter);
        provider.setVisibility(View.GONE);


        provider.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                url = providerUrlLinker.get(providerSource.get(position));
                update(url);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        splashScreen = SplashScreenFragment.newInstance();

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_frame, splashScreen, null).commit();

        toolbar.setVisibility(View.GONE);

        update(url);


    }

    private void update(String url) {
        resourceManager.loadData(parser, url, this);
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
