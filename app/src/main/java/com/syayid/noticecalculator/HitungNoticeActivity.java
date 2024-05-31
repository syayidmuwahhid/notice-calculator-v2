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
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.syayid.noticecalculator.database.BiayaProsesHandler;
import com.syayid.noticecalculator.database.DBHandler;
import com.syayid.noticecalculator.database.NoticeHandler;
import com.syayid.noticecalculator.models.BiayaProses;
import com.syayid.noticecalculator.models.Notices;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HitungNoticeActivity extends AppCompatActivity {
    private Context context;
    private TextView tvWilayah;
    private LinearLayout container;
    private Button addSpinnerButton;
    private Button submitButton;
    private Button resetButton;
    private List<Spinner> spinnerList;
    private List<EditText> numberInputList;
    private LinearLayout spinnerLayoutMaster;
    private DBHandler noticeHandler, biayaProsesHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitung_notice);
        context = this;
        noticeHandler = NoticeHandler.getInstance(context);
        biayaProsesHandler = BiayaProsesHandler.getInstance(context);

        String wilayah = getIntent().getStringExtra("wilayah");

        tvWilayah = findViewById(R.id.wilayah);
        tvWilayah.setText(wilayah);

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
//
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
        List<Notices> notices = noticeHandler.getDatas();

        for (Notices data : notices) {
            noticeList.add(data.getTipe());
        }

        LinearLayout spinnerLayout = new LinearLayout(context);
        spinnerLayout.setOrientation(LinearLayout.HORIZONTAL);

        Spinner newSpinner = new Spinner(context);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, noticeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newSpinner.setAdapter(adapter);

        LinearLayout.LayoutParams layoutParamsSpinner = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsSpinner.weight = 0.7f;
        newSpinner.setLayoutParams(layoutParamsSpinner);

        EditText numberInput = new EditText(context);
        LinearLayout.LayoutParams layoutParamsNum = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsNum.weight = 0.2f;
        numberInput.setLayoutParams(layoutParamsNum);
        numberInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        numberInput.setHint("0");
        numberInput.setGravity(Gravity.CENTER);

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
        numberInput.setId(numberInputId);
        removeBt.setId(removeBtId);

        removeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerLayoutMaster.removeView(spinnerLayout);
                spinnerList.remove(newSpinner);
                numberInputList.remove(numberInput);
            }
        });

        spinnerLayout.addView(newSpinner);
        spinnerLayout.addView(numberInput);
        spinnerLayout.addView(removeBt);

        spinnerLayoutMaster.addView(spinnerLayout);
        spinnerList.add(newSpinner);
        numberInputList.add(numberInput);
    }


    private void handleSubmit() {
        ArrayList<String> notices = new ArrayList<>();
        ArrayList<String> jmlNotices = new ArrayList<>();


        for (int i = 0; i < spinnerList.size(); i++) {
            Spinner spinner = spinnerList.get(i);
            String selectedItem = spinner.getSelectedItem().toString();

            EditText numberInput = numberInputList.get(i);
            String number = numberInput.getText().toString();

            if (number.equalsIgnoreCase("")
                    || number.equalsIgnoreCase("0")
                    || selectedItem.equalsIgnoreCase("Pilih Type") ){
                continue;
            }

            notices.add(selectedItem);
            jmlNotices.add(number);
        }

        //validasi duplikasi spinner
        Set<String> selectedNotice = new HashSet<>();
        boolean hasDuplicate = false;

        // Memeriksa kursi yang dipilih dalam setiap spinner
        for (Spinner spinner : spinnerList) {
            String selectedSeat = spinner.getSelectedItem().toString();

            // Memeriksa apakah kursi telah dipilih sebelumnya
            if (!selectedNotice.add(selectedSeat)) {
                hasDuplicate = true;
                break; // Ada duplikat, keluar dari loop
            }
        }

        if (hasDuplicate) {
            // Menampilkan pesan bahwa ada kursi yang duplikat
            Toast.makeText(context, "Ada Notice yang duplikat, harap pilih kursi yang berbeda.", Toast.LENGTH_LONG).show();
        } else {
            // Lanjutkan dengan proses checkout karena tidak ada kursi yang duplikat
            Intent intent = new Intent(this, HasilNoticeActivity.class);
            intent.putStringArrayListExtra("notices", notices);
            intent.putStringArrayListExtra("jmlNotices", jmlNotices);
            intent.putExtra("lokasi", tvWilayah.getText().toString());
            startActivity(intent);
        }


    }

    private void setHeader() {
        LinearLayout layout = new LinearLayout(context);

        LinearLayout.LayoutParams layout1 = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout1.weight = 0.7f;

        LinearLayout.LayoutParams layout2 = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout2.weight = 0.3f;

        //Context context, String text, TableRow.LayoutParams params, int style, int size, int align
        layout.addView(setTV(context, "Pilih Notice", layout1, Typeface.BOLD, 15, 0));
        layout.addView(setTV(context, "Jumlah", layout2, Typeface.BOLD, 15, 0));
        spinnerLayoutMaster.addView(layout);

    }
}