package com.example.robertvacareanu.currencyconverter.business.jsonparser;

import org.json.JSONObject;

import java.util.HashMap;

public abstract class JsonParser {


    public static final int RESULT_OK = 0;

    public static final int RESULT_ERROR = 1;

    public abstract int parse(JSONObject jsonObject);

    public abstract HashMap<String, Double> getResults();


}
