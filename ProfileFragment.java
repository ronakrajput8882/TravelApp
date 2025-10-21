package com.example.travelapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.travelapp.R;
import com.example.travelapp.activities.LoginActivity;
import com.example.travelapp.database.DBHelper;
import com.example.travelapp.models.User;
import com.example.travelapp.utils.SessionManager;

public class ProfileFragment extends Fragment {

    private TextView tvName, tvEmail;
    private Button btnLogout;
    private SessionManager sessionManager;
    private DBHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvName = view.findViewById(R.id.tv_profile_name);
        tvEmail = view.findViewById(R.id.tv_profile_email);
        btnLogout = view.findViewById(R.id.btn_logout);

        sessionManager = new SessionManager(getContext());
        dbHelper = new DBHelper(getContext());

        loadUserProfile();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logoutUser();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();
                Toast.makeText(getContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void loadUserProfile() {
        String userId = sessionManager.getUserId();
        if (userId != null) {
            User user = dbHelper.getUserById(userId);
            if (user != null) {
                tvName.setText(user.getName());
                tvEmail.setText(user.getEmail());
            } else {
                Toast.makeText(getContext(), "User data not found.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "User not logged in.", Toast.LENGTH_SHORT).show();
            // Optional: redirect to login
        }
    }
}