package com.syayid.noticecalculator.tools;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import java.text.NumberFormat;

import java.util.Locale;

public class FormatRp implements TextWatcher {
    private EditText editText;
    private Locale locale;
    private NumberFormat formatter;

    public FormatRp(EditText editText) {
        this.editText = editText;
        this.locale = new Locale("id", "ID");
        this.formatter = NumberFormat.getCurrencyInstance(locale);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // Nothing to do here
        System.out.println("Before : " + s);

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // Nothing to do here
        System.out.println("After : " + s);
    }

    @Override
    public void afterTextChanged(Editable editable) {
        editText.removeTextChangedListener(this);

        String text = editable.toString().replaceAll("[^\\d]", "");
        double parsed = parseToDouble(text);
        String formatted = formatter.format(parsed);

        editText.setText(formatted);
        editText.setSelection(formatted.length());

        editText.addTextChangedListener(this);
    }

    private double parseToDouble(String text) {
        try {
            return Double.parseDouble(text) / 1000; // Divide by 1000 to remove decimal part
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}
