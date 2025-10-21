package com.example.travelapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.travelapp.R;
import com.example.travelapp.adapters.BookingAdapter;
import com.example.travelapp.database.DBHelper;
import com.example.travelapp.databinding.FragmentDashboardBinding; // Import ViewBinding
import com.example.travelapp.models.Booking;
import com.example.travelapp.utils.SessionManager;
import java.util.Collections;
import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding; // Use ViewBinding
    private SessionManager sessionManager;
    private DBHelper dbHelper;
    private BookingAdapter bookingAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout using ViewBinding
        binding = FragmentDashboardBinding.inflate(inflater, container, false);

        // Initialize helpers
        sessionManager = new SessionManager(getContext());
        dbHelper = new DBHelper(getContext());

        // Setup the RecyclerView
        setupRecyclerView();

        // Setup button click listeners
        setupClickListeners();

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Load or refresh the bookings every time the fragment is shown
        loadBookings();
    }

    private void setupRecyclerView() {
        binding.bookingsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // Initialize adapter with an empty list
        bookingAdapter = new BookingAdapter(getContext(), Collections.emptyList(), dbHelper);
        binding.bookingsRecyclerView.setAdapter(bookingAdapter);
    }

    private void setupClickListeners() {
        // Listener for the "Clear All" button in the header
        binding.btnClearBookings.setOnClickListener(v -> {
            dbHelper.deleteAllBookings();
            Toast.makeText(getContext(), "All bookings cleared.", Toast.LENGTH_SHORT).show();
            loadBookings(); // Refresh the list to show the empty state
        });

        // Listener for the "Explore Trips" button in the empty state view
        binding.btnExploreTrips.setOnClickListener(v -> {
            // Use NavController to navigate to the trips fragment
            // This assumes your trips fragment has the ID 'navigation_trips' in your nav_graph
            NavHostFragment.findNavController(this).navigate(R.id.navigation_trips);
        });
    }

    private void loadBookings() {
        // Show the loading indicator and hide other views
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.bookingsRecyclerView.setVisibility(View.GONE);
        binding.layoutNoBookings.setVisibility(View.GONE);

        String userId = sessionManager.getUserId();
        if (userId != null) {
            // In a real app, this should be on a background thread
            List<Booking> bookingList = dbHelper.getBookingsByUserId(userId);
            binding.progressBar.setVisibility(View.GONE);

            if (bookingList.isEmpty()) {
                // Show the empty state
                binding.layoutNoBookings.setVisibility(View.VISIBLE);
            } else {
                // Show the content
                binding.bookingsRecyclerView.setVisibility(View.VISIBLE);
                // Update the adapter with the new data
                bookingAdapter = new BookingAdapter(getContext(), bookingList, dbHelper);
                binding.bookingsRecyclerView.setAdapter(bookingAdapter);
            }
        } else {
            // Handle case where user is not logged in
            binding.progressBar.setVisibility(View.GONE);
            binding.layoutNoBookings.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Nullify the binding object in fragments to avoid memory leaks
        binding = null;
    }
}