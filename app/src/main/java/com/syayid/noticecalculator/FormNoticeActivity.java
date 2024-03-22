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
                    Toast.makeText(context, "Tipe Sudah Ada!", Toast.LENGTH_LONG).show();
                    return false;
                }
            }

            //update
            Notices update_notice = new Notices();
            update_notice.setTipe(edTipe.getText().toString());
            update_notice.setHarga(Double.parseDouble(edNotice.getText().toString()));
            update_notice.setProgresif(Double.parseDouble(edProgresif.getText().toString()));
            Toast.makeText(context, getIntent().getStringExtra("update"), Toast.LENGTH_LONG).show();
            noticeHandler.update(update_notice, getIntent().getStringExtra("update"));
            return true;

        } catch (Exception e) {
           Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private void submitNotice() {
        try {
            Notices notices = (Notices) noticeHandler.getData(edTipe.getText().toString());
            if (notices.getTipe().equalsIgnoreCase(edTipe.getText().toString())) {
                Toast.makeText(context, "Tipe Sudah Ada!", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            if (e.getMessage().equalsIgnoreCase("Index 0 requested, with a size of 0")) {
                Notices new_notice = new Notices();
                new_notice.setTipe(edTipe.getText().toString());
                new_notice.setHarga(Double.parseDouble(edNotice.getText().toString()));
                new_notice.setProgresif(Double.parseDouble(edProgresif.getText().toString()));

                try {
                    noticeHandler.add(new_notice);
                    Toast.makeText(context, "Berhasil Menambah Notice, Silakan Refresh Halaman", Toast.LENGTH_LONG).show();
                    finish();
                } catch (Exception er) {
                    Toast.makeText(context, er.getMessage(), Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}