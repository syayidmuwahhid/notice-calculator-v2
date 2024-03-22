package com.syayid.noticecalculator.ui.notice;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.syayid.noticecalculator.FormNoticeActivity;
import com.syayid.noticecalculator.database.DBHandler;
import com.syayid.noticecalculator.database.NoticeHandler;
import com.syayid.noticecalculator.database.UserHandler;
import com.syayid.noticecalculator.databinding.FragmentNoticeBinding;
import com.syayid.noticecalculator.models.Notices;
import com.syayid.noticecalculator.models.Users;
import com.syayid.noticecalculator.tools.GlobalData;
import com.syayid.noticecalculator.tools.DialogListener;

import java.util.List;

public class NoticeFragment extends Fragment {

    private FragmentNoticeBinding binding;
    private TableLayout tabelKontainer;
    private Context context;
    private DBHandler userHandler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNoticeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        context = root.getContext();
        userHandler = UserHandler.getInstance(context);

        tabelKontainer = binding.tabelkontainer;
        tabelKontainer.removeAllViews();
        tabelKontainer.addView(setupTableHeader());

        getDataNotice();

        Button btnTambahNotice = binding.btnTambahNotice;

        btnTambahNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FormNoticeActivity.class);
                GlobalData.showDialogPassword(context, new GlobalData.Redirect(context, intent));
            }
        });

        return root;
    }

    private void getDataNotice() {
        DBHandler noticeHandler = new NoticeHandler(context);
        List<Notices> notices = noticeHandler.getDatas();
        for (Notices noticeData : notices) {
            Notices notice = (Notices) noticeData;

            TableRow tr = new TableRow(context);

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
                                    Intent intent = new Intent(context, FormNoticeActivity.class);
                                    intent.putExtra("update", notice.getTipe());
                                    GlobalData.showDialogPassword(context, new GlobalData.Redirect(context, intent));
                                    break;
                                case 1:
                                    // Tindakan untuk opsi Hapus
                                    GlobalData.generateKonfirmDialog(context, "Apakah anda yakin ingin mengapus " + notice.getTipe() + "?", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            GlobalData.showDialogPassword(context, new GlobalData.HapusNotice(context, notice.getTipe()));
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

            tr.addView(setTV(notice.getTipe(), null, Typeface.NORMAL, 20, 0));
            tr.addView(setTV(GlobalData.formatRp(notice.getHarga()), null, Typeface.NORMAL, 20, Gravity.RIGHT));
            tr.addView(setTV(GlobalData.formatRp(notice.getProgresif()), null, Typeface.NORMAL, 20, Gravity.RIGHT));
            tabelKontainer.addView(tr);
        }
    }

    private TableRow setupTableHeader() {
        TableRow tr = new TableRow(context);

        tr.addView(setTV("Tipe", null, Typeface.BOLD, 20, 0));
        tr.addView(setTV("Notice", null, Typeface.BOLD, 20, 0));
        tr.addView(setTV("Progresif", null, Typeface.BOLD, 20, 0));
        return tr;
    }

    private TextView setTV(String text, TableRow.LayoutParams params, int style, int size, int align) {
        TextView tv = new TextView(context);
        tv.setText(text);

        // Menetapkan parameter pada TextView
        if (params == null) {
            // Menetapkan parameter default jika params bernilai null
            params = new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            );

        }
        // Mengatur margin pada TextView (dalam contoh ini, margin kiri sebesar 10dp)
        params.setMargins(10, 10, 10, 10); // left, top, right, bottom

        if (align != 0) {
            tv.setGravity(align);
        }

        tv.setLayoutParams(params);

        // Mengatur ukuran teks (dalam contoh ini, ukuran teks 20sp)
        tv.setTextSize(size);

        // Mengatur gaya teks (dalam contoh ini, menggunakan teks bold)
        tv.setTypeface(null, style);
        return tv;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}