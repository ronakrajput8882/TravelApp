package com.example.travelapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.travelapp.activities.TripDetailActivity;
import com.example.travelapp.adapters.TripAdapter;
import com.example.travelapp.database.DBHelper;
import com.example.travelapp.databinding.FragmentTripsBinding; // Import ViewBinding
import com.example.travelapp.models.Trip;
import java.util.List;

// ✅ Implement the click listener interface
public class TripFragment extends Fragment implements TripAdapter.OnTripClickListener {

    private FragmentTripsBinding binding; // Use ViewBinding
    private DBHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTripsBinding.inflate(inflater, container, false);
        dbHelper = new DBHelper(getContext());

        setupRecyclerView();
        loadTrips();

        return binding.getRoot();
    }

    private void setupRecyclerView() {
        binding.tripsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void loadTrips() {
        // Note: For a real app, run this on a background thread!
        List<Trip> trips = dbHelper.getAllTrips();
        if (trips != null && !trips.isEmpty()) {
            // ✅ Pass 'this' as the click listener
            TripAdapter tripAdapter = new TripAdapter(getContext(), trips, this);
            binding.tripsRecyclerView.setAdapter(tripAdapter);
        }
    }

    // ✅ Handle the click event here
    @Override
    public void onTripClick(Trip trip) {
        Intent intent = new Intent(getActivity(), TripDetailActivity.class);
        intent.putExtra("trip", trip);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}