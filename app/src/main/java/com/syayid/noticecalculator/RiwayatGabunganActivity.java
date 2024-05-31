package com.syayid.noticecalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.syayid.noticecalculator.database.ArsipGabunganDetailHandler;
import com.syayid.noticecalculator.database.ArsipGabunganHandler;
import com.syayid.noticecalculator.database.DBHandler;
import com.syayid.noticecalculator.models.Arsip;
import com.syayid.noticecalculator.models.ArsipAdapter;
import com.syayid.noticecalculator.models.ArsipGabunganDetail;

import java.util.ArrayList;
import java.util.List;

public class RiwayatGabunganActivity extends AppCompatActivity {
    private DBHandler arsipHandler;
    private Context context;
    private RecyclerView recyclerView;
    private ArsipAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_gabungan);

        context = this;
        arsipHandler = ArsipGabunganHandler.getInstance(context);

        List<Arsip> transaksiList = arsipHandler.getDatas();

        recyclerView = findViewById(R.id.recyclerTransaksi);

        adapter = new ArsipAdapter(transaksiList, new ArsipAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int itemId) {
                try {
                    DBHandler arsipHandler = ArsipGabunganHandler.getInstance(context);
                    DBHandler arsipDetailHandler = ArsipGabunganDetailHandler.getInstance(context);

                    String id_arsip = String.valueOf(itemId);
                    Arsip arsip = (Arsip) arsipHandler.getData(id_arsip);
                    List<ArsipGabunganDetail> arsipDetails = arsipDetailHandler.getDatas("id_arsip_gabungan", id_arsip);

                    ArrayList<String> notices = new ArrayList<>();
                    ArrayList<String> jmlNotices = new ArrayList<>();
                    ArrayList<String> progresifs = new ArrayList<>();

                    for(ArsipGabunganDetail ad : arsipDetails) {
                        notices.add(ad.getTipe());
                        jmlNotices.add(String.valueOf(ad.getJumlah()));
                        progresifs.add(String.valueOf(ad.getProgresif_ke()));
                    }

                    Intent intent = new Intent(context, HasilGabunganActivity.class);
                    intent.putStringArrayListExtra("notices", notices);
                    intent.putStringArrayListExtra("jmlNotices", jmlNotices);
                    intent.putStringArrayListExtra("progresifs", progresifs);
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