package com.example.travelapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.travelapp.R;
import com.example.travelapp.databinding.ActivityTripDetailBinding; // 1. Import ViewBinding
import com.example.travelapp.models.Trip;
import java.util.Locale;

public class TripDetailActivity extends AppCompatActivity {

    private ActivityTripDetailBinding binding; // 2. Use the binding class
    private Trip trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 3. Inflate the layout with ViewBinding
        binding = ActivityTripDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // --- Setup the new Toolbar ---
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed()); // Handle the back arrow click

        // --- Get Trip Data ---
        trip = (Trip) getIntent().getSerializableExtra("trip");

        if (trip != null) {
            populateUi(trip);
        } else {
            Toast.makeText(this, "Error: Trip data not found.", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity if there's no data
        }

        // --- Set Click Listener for the "Book Now" button ---
        binding.btnBookNow.setOnClickListener(v -> {
            if (trip != null) {
                Intent bookingIntent = new Intent(TripDetailActivity.this, BookingActivity.class);
                bookingIntent.putExtra("trip", trip);
                startActivity(bookingIntent);
            }
        });
    }

    /**
     * Helper method to fill all the views with data from the Trip object.
     */
    private void populateUi(Trip trip) {
        // ✅ 4. Access all views safely through the 'binding' object

        // Set titles
        binding.collapsingToolbar.setTitle(trip.getTitle());
        binding.tvTripDetailTitle.setText(trip.getTitle());

        // Set description
        binding.tvTripDetailDescription.setText(trip.getDescription());

        // Set data in the included layout
        binding.includeKeyDetails.tvTripDetailDuration.setText(trip.getDuration());
        binding.includeKeyDetails.tvTripDetailRating.setText(String.valueOf(trip.getRating()));

        // Set price in the bottom bar
        binding.tvTripDetailPrice.setText("$" + String.format(Locale.US, "%,.0f", trip.getPrice()));

        // Load the image with Glide
        Glide.with(this)
                .load(trip.getImageUrl())
                .placeholder(R.drawable.placeholder_trip_image)
                .into(binding.ivTripDetailImage);
    }
}