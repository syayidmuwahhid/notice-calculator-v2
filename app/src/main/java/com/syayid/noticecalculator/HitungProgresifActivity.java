package com.syayid.noticecalculator;

import static com.syayid.noticecalculator.tools.GlobalData.setTV;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.syayid.noticecalculator.database.BiayaProsesHandler;
import com.syayid.noticecalculator.database.DBHandler;
import com.syayid.noticecalculator.database.NoticeHandler;
import com.syayid.noticecalculator.models.Notices;

import java.util.ArrayList;
import java.util.List;

public class HitungProgresifActivity extends AppCompatActivity {
    private Context context;
    private LinearLayout container;
    private Button addSpinnerButton;
    private Button submitButton;
    private Button resetButton;
    private List<Spinner> spinnerList;
    private List<Spinner> numberInputList;
    private LinearLayout spinnerLayoutMaster;
    private DBHandler noticeHandler, biayaProsesHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitung_progresif);

        context = this;
        noticeHandler = NoticeHandler.getInstance(context);
        biayaProsesHandler = BiayaProsesHandler.getInstance(context);

        container = findViewById(R.id.container);
        spinnerLayoutMaster = findViewById(R.id.spinnerLayoutmaster);
        addSpinnerButton = findViewById(R.id.addSpinnerButton);
        submitButton = findViewById(R.id.submitButton);
        resetButton = findViewById(R.id.resetButton);

        spinnerList = new ArrayList<>();
        numberInputList = new ArrayList<>();
        setHeader();

        addSpinnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewSpinner();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSubmit();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Membangun kotak dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Reset"); // Ganti dengan judul yang sesuai
                builder.setMessage("Yakin mereset semua data?"); // Ganti dengan pesan yang sesuai

                // Tombol Positif (jika diperlukan)
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Tindakan yang diambil saat tombol OK ditekan
//                        dialog.dismiss(); // Menutup dialog
                        spinnerList = new ArrayList<>();
                        numberInputList = new ArrayList<>();
                        spinnerLayoutMaster.removeAllViews();
                        setHeader();
                        addNewSpinner();
                    }
                });

                // Tombol Negatif (jika diperlukan)
                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Tindakan yang diambil saat tombol Batal ditekan
                        dialog.dismiss(); // Menutup dialog
                    }
                });

                // Menampilkan kotak dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        addNewSpinner();

    }

    private void addNewSpinner() {
        ArrayList<String> noticeList = new ArrayList<>();
        ArrayList<String> progresifList = new ArrayList<>();
        List<Notices> notices = noticeHandler.getDatas();

        for (Notices data : notices) {
            noticeList.add(data.getTipe());
        }

        progresifList.add("2");
        progresifList.add("3");
        progresifList.add("4");
        progresifList.add("5");

        LinearLayout spinnerLayout = new LinearLayout(context);
        spinnerLayout.setOrientation(LinearLayout.HORIZONTAL);

        Spinner newSpinner = new Spinner(context);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, noticeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newSpinner.setAdapter(adapter);

        LinearLayout.LayoutParams layoutParamsSpinner = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsSpinner.weight = 0.6f;
        newSpinner.setLayoutParams(layoutParamsSpinner);


        Spinner progSpinner = new Spinner(context);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, progresifList);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        progSpinner.setAdapter(adapter2);

        LinearLayout.LayoutParams layoutParamsSpinner2 = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsSpinner2.weight = 0.3f;
        progSpinner.setLayoutParams(layoutParamsSpinner2);


        Button removeBt = new Button(context);
        LinearLayout.LayoutParams layoutParamsBt = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsBt.weight = 0.1f;
        removeBt.setLayoutParams(layoutParamsBt);
        removeBt.setText("🗑️");
        int redColor = Color.parseColor("#FF5733");
        ColorStateList redColorStateList = ColorStateList.valueOf(redColor);
        removeBt.setBackgroundTintList(redColorStateList);

        // Set ID yang unik untuk setiap elemen
        int spinnerId = View.generateViewId();
        int numberInputId = View.generateViewId();
        int removeBtId = View.generateViewId();
        newSpinner.setId(spinnerId);
        progSpinner.setId(numberInputId);
        removeBt.setId(removeBtId);

        removeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerLayoutMaster.removeView(spinnerLayout);
                spinnerList.remove(newSpinner);
                numberInputList.remove(progSpinner);
            }
        });

        spinnerLayout.addView(newSpinner);
        spinnerLayout.addView(progSpinner);
        spinnerLayout.addView(removeBt);

        spinnerLayoutMaster.addView(spinnerLayout);
        spinnerList.add(newSpinner);
        numberInputList.add(progSpinner);
    }

    private void setHeader() {
        LinearLayout layout = new LinearLayout(context);

        LinearLayout.LayoutParams layout1 = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout1.weight = 0.6f;

        LinearLayout.LayoutParams layout2 = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout2.weight = 0.4f;

        //Context context, String text, TableRow.LayoutParams params, int style, int size, int align
        layout.addView(setTV(context, "Pilih Notice", layout1, Typeface.BOLD, 15, 0));
        layout.addView(setTV(context, "Progresif ke-", layout2, Typeface.BOLD, 15, 0));
        spinnerLayoutMaster.addView(layout);

    }


    private void handleSubmit() {
        ArrayList<String> notices = new ArrayList<>();
        ArrayList<String> jmlNotices = new ArrayList<>();


        for (int i = 0; i < spinnerList.size(); i++) {
            Spinner spinner = spinnerList.get(i);
            String selectedItem = spinner.getSelectedItem().toString();

            Spinner numberInput = numberInputList.get(i);
            String number = numberInput.getSelectedItem().toString();

            if (number.equalsIgnoreCase("")
                    || number.equalsIgnoreCase("0")
                    || selectedItem.equalsIgnoreCase("Pilih Type") ){
                continue;
            }

            notices.add(selectedItem);
            jmlNotices.add(number);
        }


            // Lanjutkan dengan proses checkout karena tidak ada kursi yang duplikat
            Intent intent = new Intent(this, HasilProgresifActivity.class);
            intent.putStringArrayListExtra("notices", notices);
            intent.putStringArrayListExtra("jmlNotices", jmlNotices);
            startActivity(intent);



    }
}