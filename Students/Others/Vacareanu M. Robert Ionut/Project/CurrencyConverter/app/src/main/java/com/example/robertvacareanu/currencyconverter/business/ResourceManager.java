package com.example.robertvacareanu.currencyconverter.business;

import android.content.Context;
import android.widget.Toast;

import com.example.robertvacareanu.currencyconverter.business.database.CurrencyDatabaseHelper;
import com.example.robertvacareanu.currencyconverter.business.jsonparser.JsonParser;
import com.example.robertvacareanu.currencyconverter.model.RatesDTO;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ResourceManager implements CurrencyConverter {


    private static final int CONNECTION_TIMEOUT = 1000;

    private RatesDTO currency = new RatesDTO();
    private Context context;


    public ResourceManager(Context context) {
        this.context = context;
        database = new CurrencyDatabaseHelper(context);
    }

    private CurrencyDatabaseHelper database;

    public void loadData(final JsonParser parser, final String url, final DataLoadingListener listener) {
        AsyncHttpClient client = new AsyncHttpClient();

        client.setMaxRetriesAndTimeout(5, CONNECTION_TIMEOUT);
        currency.setRatesDTO(null);

        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                parser.parse(response);
                currency.setRatesDTO(parser.getResults());

                database.save(response, linkDatabaseLinker.get(url));

                listener.onSuccess(currency.getRates());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                if (database.load(linkDatabaseLinker.get(url)) != null) {
                    parser.parse(database.load(linkDatabaseLinker.get(url)));
                    Toast.makeText(context, "No internet Connection.\nData loaded from previous session", Toast.LENGTH_SHORT).show();
                    listener.onSuccess(parser.getResults());
                } else {
                    Toast.makeText(context, "No internet connection.\nThere is no data to load from previous time", Toast.LENGTH_SHORT).show();
                    listener.onError();
                }
            }
        });

    }


}