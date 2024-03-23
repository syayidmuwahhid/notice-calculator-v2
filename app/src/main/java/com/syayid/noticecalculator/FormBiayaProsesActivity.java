package com.syayid.noticecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.syayid.noticecalculator.database.BiayaProsesHandler;
import com.syayid.noticecalculator.database.DBHandler;
import com.syayid.noticecalculator.models.BiayaProses;
import com.syayid.noticecalculator.models.Notices;

import java.text.DecimalFormat;

public class FormBiayaProsesActivity extends AppCompatActivity {
    private Button submitBtn;
    private EditText edWilayah, edBiaya;
    private TextView title;
    private DBHandler biayaProsesHandler;
    private DecimalFormat noDesimal = new DecimalFormat("#");
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_biaya_proses);

        edWilayah = findViewById(R.id.editTextWilayah);
        edBiaya = findViewById(R.id.editTextBiaya);
        submitBtn = findViewById(R.id.buttonSave);
        title = findViewById(R.id.title);
        biayaProsesHandler = BiayaProsesHandler.getInstance(this);

        if (getIntent().hasExtra("update")) {
            //get Data Notice
            BiayaProses biayaProses = (BiayaProses) biayaProsesHandler.getData(getIntent().getStringExtra("update"));

            //setup data to form
            title.setText("Update Biaya Proses [#" + getIntent().getStringExtra("update") + "]");
            edWilayah.setText(biayaProses.getWilayah());
            edBiaya.setText(noDesimal.format(biayaProses.getHarga()));
        }

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edWilayah.getText().toString().isEmpty() || edBiaya.getText().toString().isEmpty()) {
                    Toast.makeText(v.getContext(), "Wilayah / Biaya Wajib diisi!", Toast.LENGTH_LONG).show();
                } else {
                    //cek Tipe
                    if (getIntent().hasExtra("update")) {
                        if(updateData()) {
                            Toast.makeText(context, "Berhasil Merubah Data", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    } else {
                        submitData();
                    }
                }
            }
        });
    }

    private boolean updateData() {
        try {
            BiayaProses biayaProses = (BiayaProses) biayaProsesHandler.getData(getIntent().getStringExtra("update"));
            if (!edWilayah.getText().toString().equalsIgnoreCase(biayaProses.getWilayah())) {
                BiayaProses cek = (BiayaProses) biayaProsesHandler.getData("wilayah", edWilayah.getText().toString());
                if (cek != null && cek.getWilayah() != null) {
                    throw new Exception("Biaya Proses Sudah Ada!");
                }
            }

            //update
            BiayaProses update_biaya = new BiayaProses();
            update_biaya.setWilayah(edWilayah.getText().toString());
            update_biaya.setHarga(Double.parseDouble(edBiaya.getText().toString()));
            biayaProsesHandler.update(update_biaya, getIntent().getStringExtra("update"));

        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void submitData() {
        try {
            BiayaProses biayaProses = (BiayaProses) biayaProsesHandler.getData("wilayah", edWilayah.getText().toString());
            if (biayaProses.getWilayah() != null) {
                Toast.makeText(context, "Biaya Proses Sudah Ada!", Toast.LENGTH_LONG).show();

            } else {
                BiayaProses new_data = new BiayaProses();
                new_data.setWilayah(edWilayah.getText().toString());
                new_data.setHarga(Double.parseDouble(edBiaya.getText().toString()));

                biayaProsesHandler.add(new_data);
                Toast.makeText(context, "Berhasil Menambah Notice", Toast.LENGTH_LONG).show();
                finish();
            }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}