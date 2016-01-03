package com.example.robertvacareanu.currencyconverter.business;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class NumberTextWatcher implements TextWatcher {

    private EditText editText;

    public NumberTextWatcher(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        editText.removeTextChangedListener(this);

        if(editText.getText().toString().equals(".")){
            editText.setText("0.");
            editText.setSelection(2);
        }

        editText.addTextChangedListener(this);
    }

}