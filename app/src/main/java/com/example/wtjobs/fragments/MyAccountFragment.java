package com.example.wtjobs.fragments;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wtjobs.Login;
import com.example.wtjobs.R;
import com.google.firebase.auth.FirebaseAuth;


public class MyAccountFragment extends Fragment {



    public MyAccountFragment() {
        // Required empty public constructor
    }


    Button btnLogout;

    ImageView profileImage;

    TextView userEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_account, container, false);


        profileImage=view.findViewById(R.id.profile_image);
        userEmail=view.findViewById(R.id.userEmail);
        btnLogout=view.findViewById(R.id.btnLogout);


        String email= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        userEmail.setText(email);



        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent= new Intent(getActivity(), Login.class);
            startActivity(intent);

        });


        return view;
    }
}

