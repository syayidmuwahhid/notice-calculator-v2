package com.syayid.noticecalculator.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.syayid.noticecalculator.R;
import com.syayid.noticecalculator.database.DBHandler;
import com.syayid.noticecalculator.database.UserHandler;
import com.syayid.noticecalculator.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        HomeViewModel homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Context context = root.getContext();

        TextView textView = binding.textHome;
        textView.setText("OKKKK");
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        Button btn = binding.btnGallery;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                // Membuat instance dari Fragment tujuan
//                GalleryFragment galleryFragment = new GalleryFragment();
//
//                // Memulai transaksi Fragment
//                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
//
//                // Mengganti Fragment yang sedang ditampilkan dengan Fragment tujuan
//                transaction.replace(R.id.nav_host_fragment_content_main, galleryFragment);
//
//                // Menambahkan transaksi ke back stack
//                transaction.addToBackStack(null);
//
//                // Melakukan commit transaksi
//                transaction.commit();
//                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
//                navController.navigate(R.id.nav_gallery);
            }
        });

        // Create sample data
//        Users new_user = new Users();
//        new_user.setName("SYAYID");
//        new_user.setPassword("123");
//        new_user.setLevel(0);
        // Add sample post to the database
//        dbHandler.add(new_user);

        // Get singleton instance of database
        DBHandler userHandler = UserHandler.getInstance(context);

        // Get all posts from database
//        List<Users> user_data = userHandler.getDatas();
//        for (Users user : user_data) {
//            System.out.println(user);
//        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}