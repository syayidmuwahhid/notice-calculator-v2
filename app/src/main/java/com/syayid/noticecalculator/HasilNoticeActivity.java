package com.syayid.noticecalculator;

import static com.syayid.noticecalculator.tools.GlobalData.generateKonfirmDialog;
import static com.syayid.noticecalculator.tools.GlobalData.showDialogPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.syayid.noticecalculator.database.ArsipDetailHandler;
import com.syayid.noticecalculator.database.ArsipHandler;
import com.syayid.noticecalculator.database.BiayaProsesHandler;
import com.syayid.noticecalculator.database.DBHandler;
import com.syayid.noticecalculator.database.NoticeHandler;
import com.syayid.noticecalculator.models.Arsip;
import com.syayid.noticecalculator.models.ArsipDetail;
import com.syayid.noticecalculator.models.BiayaProses;
import com.syayid.noticecalculator.models.Notices;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HasilNoticeActivity extends AppCompatActivity {
    private LinearLayout container;
    private DBHandler noticeHandler, biayaProsesHandler;
    private Context context;
    private Button btnArsip;
    private ArrayList<String> notices, jmlNotices;
    private String lokasi;
    private DecimalFormat rupiahFormat;
    private long totalSeluruh = 0;
    private long totalAkhir = 0;
    private int jmlSeluruh = 0;
    private BiayaProses biayaProses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_notice);

        container = findViewById(R.id.container2);
        context = this;
        noticeHandler = NoticeHandler.getInstance(context);
        biayaProsesHandler = BiayaProsesHandler.getInstance(context);
        btnArsip = findViewById(R.id.btnArsip);

        Intent intent = getIntent();
        notices = intent.getStringArrayListExtra("notices");
        jmlNotices = intent.getStringArrayListExtra("jmlNotices");
        lokasi = intent.getStringExtra("lokasi");

        TextView titleWilayah = findViewById(R.id.title_wilayah);
        titleWilayah.setText(lokasi);

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

                double harga = noticeData.getHarga();
                double totalHarga = harga * Double.parseDouble(jmlNotices.get(i));
                totalSeluruh += totalHarga;
                jmlSeluruh += Double.parseDouble(jmlNotices.get(i));

                text.setText(notices.get(i));
                jml.setText(jmlNotices.get(i));
                hargaL.setText("Rp " + String.valueOf(rupiahFormat.format(harga)));
                total.setText("Rp " + String.valueOf(rupiahFormat.format(totalHarga)));

                container.addView(layout);
                layout.addView(text);
                layout.addView(jml);
                layout.addView(hargaL);
                layout.addView(total);

            }

            biayaProses = (BiayaProses) biayaProsesHandler.getData("wilayah", lokasi);
            TampilTotal(totalSeluruh, biayaProses.getHarga(), jmlSeluruh);
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

                        Arsip arsip = new Arsip();
                        //int id, String tanggal, String wilayah, double biaya_proses, int jumlah_faktur, double total_seluruh
                        arsip.setTanggal(tanggal);
                        arsip.setWilayah(lokasi);
                        arsip.setBiaya_proses(biayaProses.getHarga());
                        arsip.setJumlah_faktur(jmlSeluruh);
                        arsip.setTotal_seluruh(totalAkhir);

                        try {
                            DBHandler arsipHandler = ArsipHandler.getInstance(context);
                            arsipHandler.add(arsip);
                            List<Arsip> oldData = arsipHandler.getDatas();
                            int idArsip = oldData.size();
                            for (int i=0; i<notices.size(); i++){
                                Notices noticeData = (Notices) noticeHandler.getData("tipe", notices.get(i));
                                double harga = noticeData.getHarga();
                                double totalHarga = harga * Double.parseDouble(jmlNotices.get(i));

                                //int id, int id_arsip, String tipe, int jumlah, double harga, double total
                                ArsipDetail arsipDetail = new ArsipDetail();
                                arsipDetail.setId_arsip(idArsip);
                                arsipDetail.setTipe(notices.get(i));
                                arsipDetail.setJumlah(Integer.parseInt(jmlNotices.get(i)));
                                arsipDetail.setHarga(harga);
                                arsipDetail.setTotal(totalHarga);

                                DBHandler arsipDetailHandler = ArsipDetailHandler.getInstance(context);
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

    void TampilTotal(double total, double biayaProses, int jml){
        LinearLayout layout = new LinearLayout(this);
        LinearLayout layout2 = new LinearLayout(this);
        LinearLayout layout3 = new LinearLayout(this);
        TextView totalSeluruh = new TextView(this);
        TextView totalSeluruhTitle = new TextView(this);
        TextView biaya = new TextView(this);
        TextView biayaTitle = new TextView(this);
        TextView totalNoticeTitle = new TextView(this);
        TextView totalNotice = new TextView(this);

        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout2.setOrientation(LinearLayout.HORIZONTAL);
        layout3.setOrientation(LinearLayout.HORIZONTAL);

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
        biayaTitle.setLayoutParams(layoutParams08);
        biayaTitle.setGravity(Gravity.END);
        biayaTitle.setTypeface(boldTypeface);
        biaya.setLayoutParams(layoutParams02);
        biaya.setGravity(Gravity.END);
        biaya.setTypeface(boldTypeface);
        totalNoticeTitle.setLayoutParams(layoutParams08);
        totalNoticeTitle.setGravity(Gravity.END);
        totalNoticeTitle.setTypeface(boldTypeface);
        totalNotice.setLayoutParams(layoutParams02);
        totalNotice.setGravity(Gravity.END);
        totalNotice.setTypeface(boldTypeface);


        DecimalFormat rupiahFormat = new DecimalFormat("#,###,###");
        totalAkhir = (long) ((long) biayaProses * jml + total);

        totalSeluruhTitle.setText("Total = ");
        totalSeluruh.setText("Rp " + String.valueOf(rupiahFormat.format(total)));
        biayaTitle.setText("Biaya Proses (Rp " + rupiahFormat.format(biayaProses) + " x " + jml + ") = ");
        biaya.setText("Rp " + String.valueOf(rupiahFormat.format(biayaProses * jml)));
        totalNoticeTitle.setText("Total Seluruh = ");
        totalNotice.setText("Rp " + String.valueOf(rupiahFormat.format(totalAkhir)));

        container.addView(layout);
        layout.addView(totalSeluruhTitle);
        layout.addView(totalSeluruh);
        container.addView(layout2);
        layout2.addView(biayaTitle);
        layout2.addView(biaya);
        container.addView(layout3);
        layout3.addView(totalNoticeTitle);
        layout3.addView(totalNotice);
    }
}