package com.syayid.noticecalculator.models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.syayid.noticecalculator.R;

import java.text.DecimalFormat;
import java.util.List;

public class ArsipAdapter extends RecyclerView.Adapter<ArsipAdapter.ViewHolder>{
    private List<Arsip> dataList;
    private ArsipAdapter.OnItemClickListener listener;

    public ArsipAdapter(List<Arsip> dataList, ArsipAdapter.OnItemClickListener listener) {
        this.dataList = dataList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ArsipAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_riwayat_notice, parent, false);
        return new ArsipAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArsipAdapter.ViewHolder holder, int position) {
        Arsip data = dataList.get(position);
        holder.bind(data, listener);
//        holder.textViewName.setText(data); // Atur data ke dalam TextView atau elemen lainnya
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView wilayah, tanggal, total;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wilayah = itemView.findViewById(R.id.g_wilayah); // Sesuaikan dengan ID dari layout item grid
            tanggal = itemView.findViewById(R.id.g_tanggal); // Sesuaikan dengan ID dari layout item grid
            total = itemView.findViewById(R.id.g_total_seluruh); // Sesuaikan dengan ID dari layout item grid
        }

        public void bind(final Arsip data, final ArsipAdapter.OnItemClickListener listener) {
            DecimalFormat rupiahFormat = new DecimalFormat("#,###,###");
            wilayah.setText(data.getWilayah());
            tanggal.setText(data.getTanggal());
            total.setText("Rp " + String.valueOf(rupiahFormat.format(data.getTotal_seluruh())));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(data.getId()); // Mengirimkan ID saat item diklik
                }
            });
        }

    }

    // Interface untuk menangani klik pada item grid
    public interface OnItemClickListener {
        void onItemClick(int itemId);
    }
}
