package com.example.robertvacareanu.currencyconverter.business.jsonparser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class CurrencyJsonParser extends JsonParser {


    boolean status = true;

    private HashMap<String, Double> result = new HashMap<>();

    @Override
    public int parse(JSONObject jsonObject) {

        String base;
        JSONObject rates;

        //Clear previous results, if any
        result.clear();

        try {

            base = String.valueOf(jsonObject.get("base"));
            rates = jsonObject.getJSONObject("rates");

            Iterator it = rates.keys();

            while (it.hasNext()) {
                String key = (String) it.next();
                result.put(key, rates.getDouble(key));
            }

            //Add base if isn't already in
            if (result.get(base) == null) {
                result.put(base, 1d);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return status ? RESULT_OK : RESULT_ERROR;
    }

    @Override
    public HashMap<String, Double> getResults() {
        return result;
    }
}
