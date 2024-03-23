package com.syayid.noticecalculator.tools;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.syayid.noticecalculator.database.ArsipDetailHandler;
import com.syayid.noticecalculator.database.ArsipHandler;
import com.syayid.noticecalculator.database.BiayaProsesHandler;
import com.syayid.noticecalculator.database.DBHandler;
import com.syayid.noticecalculator.database.NoticeHandler;
import com.syayid.noticecalculator.database.UserHandler;
import com.syayid.noticecalculator.models.BiayaProses;
import com.syayid.noticecalculator.models.Notices;
import com.syayid.noticecalculator.models.Users;

import java.text.NumberFormat;
import java.util.Locale;

public class GlobalData {
    public final static Locale locale = new Locale("id", "ID");
    public final static NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);

    public static String formatRp(double Rp) {
        formatter.setMaximumFractionDigits(0);
        return formatter.format(Rp);
    }

    public static void showDialogPassword(Context context, Runnable aksi) {
        // Membuat dialog untuk meminta input password
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Masukkan Password");

        // Mengatur layout dialog
        final EditText inputPassword = new EditText(context);
        inputPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(inputPassword);

        // Menambahkan tombol "OK" pada dialog
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String password = inputPassword.getText().toString().trim();
                DBHandler userHandler = UserHandler.getInstance(context);
                Users pass = (Users) userHandler.getData("1");
                if (password.equalsIgnoreCase(pass.getPassword())) {
                    aksi.run();
                } else {
                    Toast.makeText(context, "Password Salah!!", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Menambahkan tombol "Batal" pada dialog
        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel(); // Membatalkan dialog saat tombol "Batal" ditekan
            }
        });

        // Menampilkan dialog
        builder.show();
    }

    public static void generateKonfirmDialog(Context context, String title, DialogInterface.OnClickListener accept, DialogInterface.OnClickListener reject) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Konfirmasi");
        builder.setMessage(title);

        // Menambahkan tombol "OK"
        builder.setPositiveButton("OK", accept);

        // Menambahkan tombol "Batal" (atau "Cancel")
        builder.setNegativeButton("Batal", reject != null ? reject : new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Membuat dan menampilkan dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static TextView setTV(Context context, String text, TableRow.LayoutParams params, int style, int size, int align) {
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
        params.setMargins(15, 10, 15, 10); // left, top, right, bottom

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

    public static void InitData(Context context) {
        DBHandler noticeHandler = NoticeHandler.getInstance(context);
        DBHandler userHandler = UserHandler.getInstance(context);
        DBHandler biayaProsesHandler = BiayaProsesHandler.getInstance(context);
        DBHandler arsipHandler = ArsipHandler.getInstance(context);
        DBHandler arsipDetailHandler = ArsipDetailHandler.getInstance(context);

        noticeHandler.empty();
        userHandler.empty();
        biayaProsesHandler.empty();
        arsipHandler.empty();
        arsipDetailHandler.empty();

        //setup notice
        final String[][] noticeList = {
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

        for (String[] item : noticeList) {
            Notices notice = new Notices();
            notice.setTipe(item[0]);
            notice.setHarga(Double.parseDouble(item[1]));
            notice.setProgresif(Double.parseDouble(item[2]));
            noticeHandler.add(notice);
        }

        //setup user
        Users new_user = new Users();
        new_user.setId(1);
        new_user.setName("admin");
        new_user.setPassword("123456");
        new_user.setLevel(0);
        userHandler.add(new_user);

        //setup biaya proses
        final String[][] biayaProsesList = {
                {"KABUPATEN SUKABUMI", "1300000"},
                {"KOTA SUKABUMI", "1340000"}
        };

        for (String[] item : biayaProsesList) {
            BiayaProses biayaProses = new BiayaProses();
            biayaProses.setWilayah(item[0]);
            biayaProses.setHarga(Double.parseDouble(item[1]));
            biayaProsesHandler.add(biayaProses);
        }
    }

//    public static void showDialogPassword(Context context, final DialogListener listener) {
//        // Membuat dialog untuk meminta input password
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("Masukkan Password");
//
//        // Mengatur layout dialog
//        final EditText inputPassword = new EditText(context);
//        inputPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//        builder.setView(inputPassword);
//
//        // Menambahkan tombol "OK" pada dialog
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                String password = inputPassword.getText().toString().trim();
//                userHandler = UserHandler.getInstance(context);
//                Users pass = (Users) userHandler.getData("1");
//                if (password.equalsIgnoreCase(pass.getPassword())) {
//                    listener.onPerform();
//                } else {
//                    Toast.makeText(context, "Password Salah!!", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//
//        // Menambahkan tombol "Batal" pada dialog
//        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel(); // Membatalkan dialog saat tombol "Batal" ditekan
//            }
//        });
//
//        // Menampilkan dialog
//        builder.show();
//    }

//    public static class HapusNotice implements DialogListener {
//        private Context context;
//        private String tipe;
//        private Runnable aksi;
//
//        public HapusNotice(Context context, String tipe) {
//            this.context = context;
//            this.tipe = tipe;
//        }
//
//        public HapusNotice(Context context, String tipe, Runnable run) {
//            this.context = context;
//            this.tipe = tipe;
//            aksi = run;
//        }
//
//        @Override
//        public void onPerform() {
//            try {
//                noticeHandler = NoticeHandler.getInstance(context);
//                noticeHandler.delete(tipe);
//                Toast.makeText(context, "Notice Berhasil dihapus!", Toast.LENGTH_LONG).show();
//                if (aksi != null) {
//                    aksi.run();
//                }
//            } catch (Exception e) {
//                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        }
//    }

//    public static class Redirect implements DialogListener {
//        private Context context;
//        private Intent intent;
//
//        public Redirect(Context context, Intent intent) {
//            this.context = context;
//            this.intent = intent;
//        }
//
//        @Override
//        public void onPerform() {
//            // Aksi yang akan dilakukan setelah memasukkan password
//            // Misalnya, pindah ke halaman lain dengan intent yang diberikan
//            context.startActivity(intent);
//        }
//    }



}
