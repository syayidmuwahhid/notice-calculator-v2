package com.syayid.noticecalculator.ui.notice;

import static com.syayid.noticecalculator.tools.GlobalData.formatRp;
import static com.syayid.noticecalculator.tools.GlobalData.generateKonfirmDialog;
import static com.syayid.noticecalculator.tools.GlobalData.setTV;
import static com.syayid.noticecalculator.tools.GlobalData.showDialogPassword;

import android.annotation.SuppressLint;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.syayid.noticecalculator.FormNoticeActivity;
import com.syayid.noticecalculator.R;
import com.syayid.noticecalculator.database.DBHandler;
import com.syayid.noticecalculator.database.NoticeHandler;
import com.syayid.noticecalculator.database.UserHandler;
import com.syayid.noticecalculator.databinding.FragmentNoticeBinding;
import com.syayid.noticecalculator.models.Notices;
import com.syayid.noticecalculator.tools.GlobalData;

import java.util.List;

public class NoticeFragment extends Fragment {

    private FragmentNoticeBinding binding;
    private TableLayout tabelKontainer;
    private Context context;
    private DBHandler userHandler, noticeHandler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNoticeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        context = root.getContext();
        userHandler = UserHandler.getInstance(context);
        noticeHandler = NoticeHandler.getInstance(context);

        tabelKontainer = binding.tabelkontainer;

        getDataNotice();

//        test();

        Button btnTambahNotice = binding.btnTambahNotice;

        btnTambahNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogPassword(context, new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(context, FormNoticeActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });

        return root;
    }

    private void test() {
        final String[][] data = {
                { "A5C02R52S1", "0", "0"},
                { "AFX12U21C08", "2375300", "76500" },
                { "B5D02K29M2", "2560500", "79000" },
                { "C1M02N41L1", "1848000", "0" },
                { "C1M02N42L1", "1976300", "62500" },
                { "F1C02N26L0", "2446500", "79000" },
                { "G2E02R21L0", "2916800", "0" },
                { "H1B02N41L0", "1976300", "62500" },
                { "H1B02N42L0", "2047500", "65000" },
                { "L1F02N36L1", "2218500", "71000" },
                { "L1F02N37L1", "2318300", "74500" },
                { "L1K02Q33L1", "2802800", "91500" },
                { "M1K03Q33L0", "3515300", "0" },
                { "N1N02Q33L1", "4056800", "0" },
                { "N1N02Q43L1", "3729000", "124000" },
                { "NF11T11C01", "1933500", "61000" },
                { "P5E02R48M1", "4284800", "0" },
                { "P5E02R49M1", "0", "0" },
                { "R5F04R24", "0", "0" },
                { "R5F04R25", "7106300", "0" },
                { "T4G02T31L0", "4156500", "0" },
                { "T5C02R37LO", "3116300", "0" },
                { "V1J02Q32L1", "3743300", "124500" },
                { "V1J02Q50L1", "3643500", "121000" },
                { "X1H02N32M1", "2546300", "82500" },
                { "Y3B02R17L0", "2916800", "0" },
        };

        noticeHandler.empty();

        // Perulangan foreach untuk mengakses setiap elemen dalam array data
        for (String[] item : data) {
            Notices notice = new Notices();
                notice.setTipe(item[0]);
                notice.setHarga(Double.parseDouble(item[1]));
                notice.setProgresif(Double.parseDouble(item[2]));
            noticeHandler.add(notice);
        }
    }

    private void getDataNotice() {
        tabelKontainer.removeAllViews();
        tabelKontainer.addView(setupTableHeader());
        String colorB1 = "#C5EBAA";
        String colorB2 = "#F6F193";
        boolean isColorB1 = true;

        List<Notices> notices = noticeHandler.getDatas();
        for (Notices noticeData : notices) {
            Notices notice = (Notices) noticeData;

            TableRow tr = new TableRow(context);
            tr.setBackgroundColor(isColorB1 ? Color.parseColor(colorB1) : Color.parseColor(colorB2));
            isColorB1 = !isColorB1;

            tr.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(notice.getTipe());
                    builder.setItems(new CharSequence[]{"Edit", "Hapus"}, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    // Tindakan untuk opsi Edit
                                    showDialogPassword(context, new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(context, FormNoticeActivity.class);
                                            intent.putExtra("update", notice.getTipe());
                                            startActivity(intent);
                                        }
                                    });
                                    break;
                                case 1:
                                    // Tindakan untuk opsi Hapus
                                    generateKonfirmDialog(context, "Apakah anda yakin ingin mengapus " + notice.getTipe() + "?", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            showDialogPassword(context, new Runnable() {
                                                @Override
                                                public void run() {
                                                    noticeHandler.delete(notice.getTipe());
                                                    Toast.makeText(context, "Notice Berhasil dihapus!", Toast.LENGTH_LONG).show();
                                                    getDataNotice();
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

            tr.addView(setTV(context, notice.getTipe(), null, Typeface.NORMAL, 18, 0));
            tr.addView(setTV(context, formatRp(notice.getHarga()), null, Typeface.NORMAL, 18, Gravity.RIGHT));
            tr.addView(setTV(context, formatRp(notice.getProgresif()), null, Typeface.NORMAL, 18, Gravity.RIGHT));
            tabelKontainer.addView(tr);
        }
    }

    private TableRow setupTableHeader() {
        TableRow tr = new TableRow(context);

        tr.addView(setTV(context,"Tipe", null, Typeface.BOLD, 19, Gravity.CENTER));
        tr.addView(setTV(context,"Notice", null, Typeface.BOLD, 19, Gravity.CENTER));
        tr.addView(setTV(context,"Progresif", null, Typeface.BOLD, 19, Gravity.CENTER));
        return tr;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDataNotice();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}