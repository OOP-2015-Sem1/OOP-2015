package com.example.robertvacareanu.currencyconverter.business;

import java.util.HashMap;

public interface DataLoadingListener {


    void onError();

    void onSuccess(HashMap<String, Double> results);



}
