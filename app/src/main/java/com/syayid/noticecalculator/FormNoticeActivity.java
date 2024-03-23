package com.syayid.noticecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.syayid.noticecalculator.database.DBHandler;
import com.syayid.noticecalculator.database.NoticeHandler;
import com.syayid.noticecalculator.models.BiayaProses;
import com.syayid.noticecalculator.models.Notices;
import com.syayid.noticecalculator.tools.FormatRp;

import java.text.DecimalFormat;

public class FormNoticeActivity extends AppCompatActivity {
    private Button submitBtn;
    private EditText edTipe, edNotice, edProgresif;
    private TextView title;
    private DBHandler noticeHandler;
    private DecimalFormat noDesimal = new DecimalFormat("#");
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_notice);

        edTipe = findViewById(R.id.editTextTipe);
        edNotice = findViewById(R.id.editTextHarga);
        edProgresif = findViewById(R.id.editTextProgresif);
        submitBtn = findViewById(R.id.buttonSave);
        title = findViewById(R.id.title);
        noticeHandler = NoticeHandler.getInstance(this);

        if (getIntent().hasExtra("update")) {
            //get Data Notice
            Notices notice = (Notices) noticeHandler.getData(getIntent().getStringExtra("update"));

            //setup data to form
            title.setText("Update Notice [#" + getIntent().getStringExtra("update") + "]");
            edTipe.setText(notice.getTipe());
            edNotice.setText(noDesimal.format(notice.getHarga()));
            edProgresif.setText(noDesimal.format(notice.getProgresif()));
        }

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edTipe.getText().toString().isEmpty() || edNotice.getText().toString().isEmpty() || edProgresif.getText().toString().isEmpty()) {
                    Toast.makeText(v.getContext(), "Tipe / Notice / Progresif Wajib diisi!", Toast.LENGTH_LONG).show();
                } else {
                    //cek Tipe
                    if (getIntent().hasExtra("update")) {
                        if(updateNotice()) {
                            Toast.makeText(context, "Berhasil Merubah Data", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    } else {
                        submitNotice();
                    }
                }
            }
        });
    }

    private boolean updateNotice() {
        try {
            Notices notices = (Notices) noticeHandler.getData(getIntent().getStringExtra("update"));
            if (!edTipe.getText().toString().equalsIgnoreCase(getIntent().getStringExtra("update"))) {
                if (notices.getTipe().equalsIgnoreCase(edTipe.getText().toString())) {
                    throw new Exception("Tipe Sudah Ada!");
                }
            }

            //update
            Notices update_notice = new Notices();
            update_notice.setTipe(edTipe.getText().toString());
            update_notice.setHarga(Double.parseDouble(edNotice.getText().toString()));
            update_notice.setProgresif(Double.parseDouble(edProgresif.getText().toString()));
            noticeHandler.update(update_notice, getIntent().getStringExtra("update"));

        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void submitNotice() {
        try {
            Notices notices = (Notices) noticeHandler.getData(edTipe.getText().toString());
            if (notices.getTipe() != null) {
                Toast.makeText(context, "Tipe Sudah Ada!", Toast.LENGTH_LONG).show();

            } else {
                Notices new_notice = new Notices();
                new_notice.setTipe(edTipe.getText().toString());
                new_notice.setHarga(Double.parseDouble(edNotice.getText().toString()));
                new_notice.setProgresif(Double.parseDouble(edProgresif.getText().toString()));

                noticeHandler.add(new_notice);
                Toast.makeText(context, "Berhasil Menambah Notice", Toast.LENGTH_LONG).show();
                finish();
            }

        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}