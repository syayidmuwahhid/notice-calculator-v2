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