package com.syayid.noticecalculator.ui.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.syayid.noticecalculator.HasilNoticeActivity;
import com.syayid.noticecalculator.HitungNoticeActivity;
import com.syayid.noticecalculator.R;
import com.syayid.noticecalculator.RiwayatNoticeActivity;
import com.syayid.noticecalculator.database.BiayaProsesHandler;
import com.syayid.noticecalculator.database.DBHandler;
import com.syayid.noticecalculator.database.UserHandler;
import com.syayid.noticecalculator.databinding.FragmentHomeBinding;
import com.syayid.noticecalculator.models.BiayaProses;
import com.syayid.noticecalculator.models.Users;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private DBHandler biayaProsesHandler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        HomeViewModel homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Context context = root.getContext();
        biayaProsesHandler = BiayaProsesHandler.getInstance(context);

        //get wilayah
        List<BiayaProses> biayaProses = biayaProsesHandler.getDatas();
        ArrayList<String> wilayahList = new ArrayList<>();

        for (BiayaProses data : biayaProses) {
            wilayahList.add(data.getWilayah());
        }

        Button btn_notice = binding.btnHitung;
        Button btn_progresif = binding.btnProgresif;
        Button btn_riwayatNotice = binding.btnRiwayatNotice;
        Button btn_riwayatProgresif = binding.btnRiwayatProgresif;

        btn_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Pilih Wilayah")
                        .setSingleChoiceItems(wilayahList.toArray(new String[0]), -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Tangani pemilihan opsi
                                String selectedWilayah = wilayahList.get(which);
                                Intent intent = new Intent(context, HitungNoticeActivity.class);
                                intent.putExtra("wilayah", selectedWilayah);
                                startActivity(intent);
                                Toast.makeText(context, "Wilayah dipilih: " + selectedWilayah, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                builder.show();
            }
        });

        btn_riwayatNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, RiwayatNoticeActivity.class));
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}