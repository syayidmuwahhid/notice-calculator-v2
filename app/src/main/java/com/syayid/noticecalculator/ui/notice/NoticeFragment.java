package com.syayid.noticecalculator.ui.notice;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.syayid.noticecalculator.databinding.FragmentNoticeBinding;

public class NoticeFragment extends Fragment {

    private FragmentNoticeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNoticeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Context context = root.getContext();

        TableLayout tabelKontainer = binding.tabelkontainer;
        tabelKontainer.removeAllViews();

        tabelKontainer.addView(setupTableHeader(context));

        TableRow tr2 = new TableRow(context);

        for (int i = 1; i<=5; i++) {
            TextView tv = new TextView(context);
            tv.setText("JJJJ " + i);

            tr2.addView(tv);

        }
        tabelKontainer.addView(tr2);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private TableRow setupTableHeader(Context context) {
        TableRow tr = new TableRow(context);

        // Membuat objek LayoutParams untuk menetapkan margin pada TextView
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );

        tr.addView(setTV(context, "Tipe", null, Typeface.BOLD, 20));
        tr.addView(setTV(context, "Notice", null, Typeface.BOLD, 20));
        tr.addView(setTV(context, "Progresif", null, Typeface.BOLD, 20));
        tr.addView(setTV(context, "Edit", null, Typeface.BOLD, 20));
        tr.addView(setTV(context, "Hapus", null, Typeface.BOLD, 20));
        return tr;
    }

    private TextView setTV(Context context, String text, TableRow.LayoutParams params, int style, int size) {
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

        tv.setLayoutParams(params);

        // Mengatur ukuran teks (dalam contoh ini, ukuran teks 20sp)
        tv.setTextSize(size);

        // Mengatur gaya teks (dalam contoh ini, menggunakan teks bold)
        tv.setTypeface(null, style);
        return tv;
    }
}