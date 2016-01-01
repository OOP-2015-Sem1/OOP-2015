package com.example.robertvacareanu.currencyconverter.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.robertvacareanu.currencyconverter.R;
import com.example.robertvacareanu.currencyconverter.business.CurrencyConverter;
import com.example.robertvacareanu.currencyconverter.business.NumberTextWatcher;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class CurrencyFragment extends Fragment implements CurrencyConverter {

    private static HashMap<String, Double> results;
    private static ArrayList<String> currency;
    private static String currency1 = null, currency2 = null;
    private static boolean spinnerChange = false;
    private MaterialEditText editText1, editText2;
    private DecimalFormat df = new DecimalFormat();

    public CurrencyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CurrencyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CurrencyFragment newInstance(HashMap<String, Double> data) {
        CurrencyFragment fragment = new CurrencyFragment();
        results = data;
        currency = new ArrayList<>(results.keySet());

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_currency, container, false);

        Collections.sort(currency);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, currency);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner firstCurrency = (Spinner) v.findViewById(R.id.spinner_currency_one);
        Spinner secondCurrency = (Spinner) v.findViewById(R.id.spinner_currency_two);

        firstCurrency.setAdapter(adapter);
        secondCurrency.setAdapter(adapter);

        editText1 = (MaterialEditText) v.findViewById(R.id.edit_text_one);
        editText2 = (MaterialEditText) v.findViewById(R.id.edit_text_two);

        editText1.setFloatingLabel(MaterialEditText.FLOATING_LABEL_HIGHLIGHT);
        editText2.setFloatingLabel(MaterialEditText.FLOATING_LABEL_HIGHLIGHT);

        editText1.setFloatingLabelAlwaysShown(true);
        editText2.setFloatingLabelAlwaysShown(true);

        if (currency1 == null || currency2 == null) {
            currency1 = "EUR";
            currency2 = "USD";
        }

        firstCurrency.setSelection(adapter.getPosition(currency1));
        secondCurrency.setSelection(adapter.getPosition(currency2));

        df.setGroupingSize(0);
        df.setMaximumFractionDigits(32);

        firstCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!currency1.equals(currency.get(position))) spinnerChange = true;
                currency1 = currency.get(position);
                editText1.setFloatingLabelText(currencyNameLinker.get(currency1));
                updateEditText(editText1, editText2, currency1, currency2, spinnerChange);
                spinnerChange = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        secondCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!currency2.equals(currency.get(position))) spinnerChange = true;
                currency2 = currency.get(position);

                editText2.setFloatingLabelText(currencyNameLinker.get(currency2));
                updateEditText(editText1, editText2, currency1, currency2, spinnerChange);
                spinnerChange = false;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        editText1.setHint("0");
        editText2.setHint("0");

        editText1.addTextChangedListener(new NumberTextWatcher(editText1) {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                updateEditText(editText1, editText2, currency1, currency2, false);
            }
        });

        editText2.addTextChangedListener(new NumberTextWatcher(editText2) {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                updateEditText(editText2, editText1, currency2, currency1, false);
            }
        });
        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void updateEditText(MaterialEditText source, MaterialEditText destination, String currency1, String currency2, boolean change) {
        if (source.hasFocus() || change) {
            if (source.getText().toString().equals("")) {
                destination.setText("");
            } else {
                Double result = Double.valueOf(source.getText().toString().replace(",", "")) * results.get(currency2) / results.get(currency1);
                destination.setText(df.format(result));
            }
        }
    }
}
