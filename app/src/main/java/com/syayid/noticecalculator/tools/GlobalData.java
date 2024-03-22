package com.syayid.noticecalculator.tools;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.syayid.noticecalculator.database.DBHandler;
import com.syayid.noticecalculator.database.NoticeHandler;
import com.syayid.noticecalculator.database.UserHandler;
import com.syayid.noticecalculator.models.Users;

import java.text.NumberFormat;
import java.util.Locale;

public class GlobalData {
    public final static Locale locale = new Locale("id", "ID");
    public final static NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);

    private static DBHandler userHandler, noticeHandler;

    public static String formatRp(double Rp) {
        formatter.setMaximumFractionDigits(0);
        return formatter.format(Rp);
    }

    public static void showDialogPassword(Context context, final DialogListener listener) {
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
                userHandler = UserHandler.getInstance(context);
                Users pass = (Users) userHandler.getData("1");
                if (password.equalsIgnoreCase(pass.getPassword())) {
                    listener.onPerform();
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

    public static class HapusNotice implements DialogListener {
        private Context context;
        private String tipe;

        public HapusNotice(Context context, String tipe) {
            this.context = context;
            this.tipe = tipe;
        }

        @Override
        public void onPerform() {
            try {
                noticeHandler = NoticeHandler.getInstance(context);
                noticeHandler.delete(tipe);
                Toast.makeText(context, "Notice Berhasil dihapus!", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public static class Redirect implements DialogListener {
        private Context context;
        private Intent intent;

        public Redirect(Context context, Intent intent) {
            this.context = context;
            this.intent = intent;
        }

        @Override
        public void onPerform() {
            // Aksi yang akan dilakukan setelah memasukkan password
            // Misalnya, pindah ke halaman lain dengan intent yang diberikan
            context.startActivity(intent);
        }
    }



}
