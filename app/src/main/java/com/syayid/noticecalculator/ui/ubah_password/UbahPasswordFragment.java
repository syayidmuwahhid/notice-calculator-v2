package com.syayid.noticecalculator.ui.ubah_password;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.syayid.noticecalculator.MainActivity;
import com.syayid.noticecalculator.database.DBHandler;
import com.syayid.noticecalculator.database.UserHandler;
import com.syayid.noticecalculator.databinding.FragmentUbahPasswordBinding;
import com.syayid.noticecalculator.models.Users;

public class UbahPasswordFragment extends Fragment {

    private FragmentUbahPasswordBinding binding;
    private EditText edNewPass, edRepass, edOldPass;
    private Button btnSave;
    private DBHandler userHandler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentUbahPasswordBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Context context = root.getContext();

        edNewPass = binding.editTextNewpass;
        edRepass = binding.editTextRepass;
        edOldPass = binding.editTextOldpass;
        btnSave = binding.buttonSave;

        //get user info
        userHandler = UserHandler.getInstance(context);
        Users user = (Users) userHandler.getData("1");

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cek password baru dan re pass
                try {
                    if (edNewPass.getText().toString().isEmpty() || edRepass.getText().toString().isEmpty() || edOldPass.getText().toString().isEmpty() ) {
                        throw new Exception("Semua Password Wajib diisi!");
                    }

                    if (!edNewPass.getText().toString().equals(edRepass.getText().toString())) {
                        throw new Exception("Password Baru Tidak Sama!");
                    }

                    if (!user.getPassword().equals(edOldPass.getText().toString())) {
                        throw new Exception("Password Lama Salah!");
                    }

                    user.setPassword(edNewPass.getText().toString());
                    userHandler.update(user);
                    Toast.makeText(context, "Password Berhasil diubah", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);

                } catch (Exception e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
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