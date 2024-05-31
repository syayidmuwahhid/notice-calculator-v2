package com.syayid.noticecalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.syayid.noticecalculator.database.ArsipProgresifDetailHandler;
import com.syayid.noticecalculator.database.ArsipProgresifHandler;
import com.syayid.noticecalculator.database.DBHandler;
import com.syayid.noticecalculator.models.ArsipProgresif;
import com.syayid.noticecalculator.models.ArsipProgresifAdapter;
import com.syayid.noticecalculator.models.ArsipProgresifDetail;

import java.util.ArrayList;
import java.util.List;

public class RiwayatProgresifActivity extends AppCompatActivity {
    private DBHandler arsipHandler;
    private Context context;
    private RecyclerView recyclerView;
    private ArsipProgresifAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_progresif);

        context = this;
        arsipHandler = ArsipProgresifHandler.getInstance(context);

        List<ArsipProgresif> transaksiList = arsipHandler.getDatas();

        recyclerView = findViewById(R.id.recyclerTransaksi);

        adapter = new ArsipProgresifAdapter(transaksiList, new ArsipProgresifAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int itemId) {
                try {
                    DBHandler arsipHandler = ArsipProgresifHandler.getInstance(context);
                    DBHandler arsipDetailHandler = ArsipProgresifDetailHandler.getInstance(context);

                    String id_arsip = String.valueOf(itemId);
                    ArsipProgresif arsip = (ArsipProgresif) arsipHandler.getData(id_arsip);
                    List<ArsipProgresifDetail> arsipDetails = arsipDetailHandler.getDatas("id_arsip_progresif", id_arsip);

                    ArrayList<String> notices = new ArrayList<>();
                    ArrayList<String> jmlNotices = new ArrayList<>();

                    for(ArsipProgresifDetail ad : arsipDetails) {
                        notices.add(ad.getTipe());
                        jmlNotices.add(String.valueOf(ad.getProgresif_ke()));
                    }

                    Intent intent = new Intent(context, HasilProgresifActivity.class);
                    intent.putStringArrayListExtra("notices", notices);
                    intent.putStringArrayListExtra("jmlNotices", jmlNotices);
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