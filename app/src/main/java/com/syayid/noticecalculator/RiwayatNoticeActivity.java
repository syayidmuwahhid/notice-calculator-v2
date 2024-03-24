package com.syayid.noticecalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.syayid.noticecalculator.database.ArsipDetailHandler;
import com.syayid.noticecalculator.database.ArsipHandler;
import com.syayid.noticecalculator.database.DBHandler;
import com.syayid.noticecalculator.models.Arsip;
import com.syayid.noticecalculator.models.ArsipAdapter;
import com.syayid.noticecalculator.models.ArsipDetail;

import java.util.ArrayList;
import java.util.List;

public class RiwayatNoticeActivity extends AppCompatActivity {
    private DBHandler arsipHandler;
    private Context context;
    private RecyclerView recyclerView;
    private ArsipAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_notice);
        context = this;
        arsipHandler = ArsipHandler.getInstance(context);

        List<Arsip> transaksiList = arsipHandler.getDatas();

        recyclerView = findViewById(R.id.recyclerTransaksi);

        adapter = new ArsipAdapter(transaksiList, new ArsipAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int itemId) {
                try {
                    DBHandler arsipHandler = ArsipHandler.getInstance(context);
                    DBHandler arsipDetailHandler = ArsipDetailHandler.getInstance(context);

                    String id_arsip = String.valueOf(itemId);
                    Arsip arsip = (Arsip) arsipHandler.getData(id_arsip);
                    List<ArsipDetail> arsipDetails = arsipDetailHandler.getDatas("id_arsip", id_arsip);

                    ArrayList<String> notices = new ArrayList<>();
                    ArrayList<String> jmlNotices = new ArrayList<>();

                    for(ArsipDetail ad : arsipDetails) {
                        notices.add(ad.getTipe());
                        jmlNotices.add(String.valueOf(ad.getJumlah()));
                    }

                    Intent intent = new Intent(context, HasilNoticeActivity.class);
                    intent.putStringArrayListExtra("notices", notices);
                    intent.putStringArrayListExtra("jmlNotices", jmlNotices);
                    intent.putExtra("lokasi", arsip.getWilayah());
                    intent.putExtra("riwayat", true);
                    startActivity(intent);

                } catch (Exception e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }
}