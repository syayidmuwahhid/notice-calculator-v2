package com.syayid.noticecalculator.ui.biaya_proses;

import static com.syayid.noticecalculator.tools.GlobalData.formatRp;
import static com.syayid.noticecalculator.tools.GlobalData.generateKonfirmDialog;
import static com.syayid.noticecalculator.tools.GlobalData.setTV;
import static com.syayid.noticecalculator.tools.GlobalData.showDialogPassword;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.syayid.noticecalculator.FormBiayaProsesActivity;
import com.syayid.noticecalculator.database.BiayaProsesHandler;
import com.syayid.noticecalculator.database.DBHandler;
import com.syayid.noticecalculator.database.UserHandler;
import com.syayid.noticecalculator.databinding.FragmentBiayaProsesBinding;
import com.syayid.noticecalculator.models.BiayaProses;
import com.syayid.noticecalculator.tools.GlobalData;

import java.util.List;

public class BiayaProsesFragment extends Fragment {

    private FragmentBiayaProsesBinding binding;
    private TableLayout tabelKontainer;
    private Context context;
    private DBHandler userHandler, biayaProsesHandler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBiayaProsesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        context = root.getContext();
        userHandler = UserHandler.getInstance(context);
        biayaProsesHandler = BiayaProsesHandler.getInstance(context);

        tabelKontainer = binding.tabelkontainer;

        getData();

        Button btnTambahProses = binding.btnTambahProses;

        btnTambahProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalData.showDialogPassword(context, new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(context, FormBiayaProsesActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });


        return root;
    }

    private void getData() {
        tabelKontainer.removeAllViews();
        tabelKontainer.addView(setupTableHeader());
        String colorB1 = "#C5EBAA";
        String colorB2 = "#F6F193";
        boolean isColorB1 = true;

        List<BiayaProses> biayaProses = biayaProsesHandler.getDatas();
        for (BiayaProses biayaProsesData : biayaProses) {
            BiayaProses data = (BiayaProses) biayaProsesData;

            TableRow tr = new TableRow(context);
            tr.setBackgroundColor(isColorB1 ? Color.parseColor(colorB1) : Color.parseColor(colorB2));
            isColorB1 = !isColorB1;

            tr.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(data.getWilayah());
                    builder.setItems(new CharSequence[]{"Edit", "Hapus"}, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    // Tindakan untuk opsi Edit
                                    showDialogPassword(context, new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(context, FormBiayaProsesActivity.class);
                                            intent.putExtra("update", String.valueOf(data.getId()));
                                            startActivity(intent);
                                        }
                                    });
                                    break;
                                case 1:
                                    // Tindakan untuk opsi Hapus
                                    generateKonfirmDialog(context, "Apakah anda yakin ingin mengapus " + data.getWilayah() + "?", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            showDialogPassword(context, new Runnable() {
                                                @Override
                                                public void run() {
                                                    biayaProsesHandler.delete(String.valueOf(data.getId()));
                                                    Toast.makeText(context, "Biaya Proses Berhasil dihapus!", Toast.LENGTH_LONG).show();
                                                    getData();
                                                }
                                            });
                                        }
                                    }, null);
                                    break;
                            }
                        }
                    });
                    builder.show();
                    return true; // Ubah menjadi true agar event onLongClick ditandai sebagai dikonsumsi
                }
            });

            tr.addView(setTV(context, data.getWilayah(), null, Typeface.NORMAL, 18, 0));
            tr.addView(setTV(context, formatRp(data.getHarga()), null, Typeface.NORMAL, 18, Gravity.RIGHT));
            tabelKontainer.addView(tr);
        }
    }

    private TableRow setupTableHeader() {
        TableRow tr = new TableRow(context);

        tr.addView(setTV(context,"Wilayah", null, Typeface.BOLD, 19, Gravity.CENTER));
        tr.addView(setTV(context,"Biaya Proses", null, Typeface.BOLD, 19, Gravity.CENTER));
        return tr;
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}