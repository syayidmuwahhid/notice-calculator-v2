package com.syayid.noticecalculator;

import static com.syayid.noticecalculator.tools.GlobalData.generateKonfirmDialog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.syayid.noticecalculator.database.ArsipProgresifDetailHandler;
import com.syayid.noticecalculator.database.ArsipProgresifHandler;
import com.syayid.noticecalculator.database.DBHandler;
import com.syayid.noticecalculator.database.NoticeHandler;
import com.syayid.noticecalculator.models.ArsipProgresif;
import com.syayid.noticecalculator.models.ArsipProgresifDetail;
import com.syayid.noticecalculator.models.BiayaProses;
import com.syayid.noticecalculator.models.Notices;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HasilProgresifActivity extends AppCompatActivity {
    private LinearLayout container;
    private DBHandler noticeHandler;
    private Context context;
    private Button btnArsip;
    private ArrayList<String> notices, progresifs;
    private DecimalFormat rupiahFormat;
    private long totalSeluruh = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_progresif);

        container = findViewById(R.id.container2);
        context = this;
        noticeHandler = NoticeHandler.getInstance(context);
        btnArsip = findViewById(R.id.btnArsip);

        Intent intent = getIntent();
        notices = intent.getStringArrayListExtra("notices");
        progresifs = intent.getStringArrayListExtra("jmlNotices");

        rupiahFormat = new DecimalFormat("#,###,###");

        if (getIntent().hasExtra("riwayat")) {
            btnArsip.setVisibility(View.INVISIBLE);
        }

        try {
            for (int i=0; i<notices.size(); i++){
                LinearLayout layout = new LinearLayout(this);
                TextView text = new TextView(this);
                TextView jml = new TextView(this);
                TextView hargaL = new TextView(this);
                TextView total = new TextView(this);

                layout.setOrientation(LinearLayout.HORIZONTAL);

                LinearLayout.LayoutParams layoutParams01 = new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT
                );
                layoutParams01.weight = 0.05f;
                layoutParams01.setMargins(10,10,10,10);

                LinearLayout.LayoutParams layoutParams02 = new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT
                );
                layoutParams02.weight = 0.2f;
                layoutParams02.setMargins(10,10,10,10);

                LinearLayout.LayoutParams layoutParams03 = new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT
                );
                layoutParams03.weight = 0.3f;
                layoutParams03.setMargins(10,10,10,10);

                text.setLayoutParams(layoutParams02);
                hargaL.setLayoutParams(layoutParams02);
                hargaL.setGravity(Gravity.END);
                jml.setLayoutParams(layoutParams01);
                jml.setGravity(Gravity.CENTER);
                total.setLayoutParams(layoutParams02);
                total.setGravity(Gravity.END);

                Notices noticeData = (Notices) noticeHandler.getData("tipe", notices.get(i));

                double biaya = noticeData.getProgresif();
                double totalBiaya = biaya * (Double.parseDouble(progresifs.get(i)) - 1 );
                totalSeluruh += totalBiaya;

                text.setText(notices.get(i));
                jml.setText(progresifs.get(i));
                hargaL.setText("Rp " + String.valueOf(rupiahFormat.format(biaya)));
                total.setText("Rp " + String.valueOf(rupiahFormat.format(totalBiaya)));

                container.addView(layout);
                layout.addView(text);
                layout.addView(jml);
                layout.addView(hargaL);
                layout.addView(total);

            }

            TampilTotal(totalSeluruh);
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        btnArsip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateKonfirmDialog(context, "Apakah anda yakin ingin mengarsipkan? ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Mendapatkan tanggal saat ini
                        Date currentDate = new Date();

                        // Mengonversi tanggal menjadi format yang diinginkan
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", new Locale("id", "ID"));
                        String tanggal = dateFormat.format(currentDate);

                        ArsipProgresif arsip = new ArsipProgresif();
                        //int id, String tanggal, String wilayah, double biaya_proses, int jumlah_faktur, double total_seluruh
                        arsip.setTanggal(tanggal);
                        arsip.setTotal(totalSeluruh);

                        try {
                            DBHandler arsipHandler = ArsipProgresifHandler.getInstance(context);
                            arsipHandler.add(arsip);
                            List<ArsipProgresif> oldData = arsipHandler.getDatas();
                            int idArsip = oldData.size();
                            for (int i=0; i<notices.size(); i++){
                                Notices noticeData = (Notices) noticeHandler.getData("tipe", notices.get(i));
                                double biaya = noticeData.getProgresif();
                                double totalHarga = biaya * Double.parseDouble(progresifs.get(i));

                                //int id, int id_arsip, String tipe, int jumlah, double harga, double total
                                ArsipProgresifDetail arsipDetail = new ArsipProgresifDetail();
                                arsipDetail.setId_arsip_progresif(idArsip);
                                arsipDetail.setTipe(notices.get(i));
                                arsipDetail.setProgresif_ke(Integer.parseInt(progresifs.get(i)));
                                arsipDetail.setBiaya(biaya);
                                arsipDetail.setSubtotal(totalHarga);

                                DBHandler arsipDetailHandler = ArsipProgresifDetailHandler.getInstance(context);
                                arsipDetailHandler.add(arsipDetail);
                            }
                            Toast.makeText(context, "Berhasil diarsipkan", Toast.LENGTH_LONG).show();
                            btnArsip.setVisibility(View.INVISIBLE);
                        } catch (Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e("error ", e.getMessage());
                        }
                    }
                }, null);
            }
        });

    }

    void TampilTotal(double total){
        LinearLayout layout = new LinearLayout(this);
        TextView totalSeluruh = new TextView(this);
        TextView totalSeluruhTitle = new TextView(this);

        layout.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams layoutParams08 = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams08.weight = 0.7f;
        layoutParams08.setMargins(10,10,10,10);

        LinearLayout.LayoutParams layoutParams02 = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams02.weight = 0.3f;
        layoutParams02.setMargins(10,10,10,10);

        Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);

        totalSeluruhTitle.setLayoutParams(layoutParams08);
        totalSeluruhTitle.setGravity(Gravity.END);
        totalSeluruhTitle.setTypeface(boldTypeface);
        totalSeluruh.setLayoutParams(layoutParams02);
        totalSeluruh.setGravity(Gravity.END);
        totalSeluruh.setTypeface(boldTypeface);

        DecimalFormat rupiahFormat = new DecimalFormat("#,###,###");

        totalSeluruhTitle.setText("Total = ");
        totalSeluruh.setText("Rp " + String.valueOf(rupiahFormat.format(total)));

        container.addView(layout);
        layout.addView(totalSeluruhTitle);
        layout.addView(totalSeluruh);
    }
}