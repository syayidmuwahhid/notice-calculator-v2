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

public class ArsipProgresifAdapter extends RecyclerView.Adapter<ArsipProgresifAdapter.ViewHolder>{
    private List<ArsipProgresif> dataList;
    private ArsipProgresifAdapter.OnItemClickListener listener;

    public ArsipProgresifAdapter(List<ArsipProgresif> dataList, ArsipProgresifAdapter.OnItemClickListener listener) {
        this.dataList = dataList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ArsipProgresifAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_riwayat_progresif, parent, false);
        return new ArsipProgresifAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArsipProgresifAdapter.ViewHolder holder, int position) {
        ArsipProgresif data = dataList.get(position);
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
            tanggal = itemView.findViewById(R.id.g_tanggal); // Sesuaikan dengan ID dari layout item grid
            total = itemView.findViewById(R.id.g_total_seluruh); // Sesuaikan dengan ID dari layout item grid
        }

        public void bind(final ArsipProgresif data, final ArsipProgresifAdapter.OnItemClickListener listener) {
            DecimalFormat rupiahFormat = new DecimalFormat("#,###,###");
            tanggal.setText(data.getTanggal());
            total.setText("Rp " + String.valueOf(rupiahFormat.format(data.getTotal())));

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
