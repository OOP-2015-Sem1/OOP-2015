package com.example.robertvacareanu.currencyconverter.model;


import java.util.HashMap;

public class RatesDTO {

    private HashMap<String, Double> rates;

    public RatesDTO() {
        rates = new HashMap<>();
    }

    public void setRatesDTO(HashMap<String, Double> rates) {
        this.rates = rates;
    }

    public HashMap<String, Double> getRates() {
        return rates;
    }


}
