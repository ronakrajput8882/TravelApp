package com.example.travelapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelapp.R;
import com.example.travelapp.databinding.ActivityBookingBinding; // Use ViewBinding
import com.google.android.material.button.MaterialButton;

public class BookingActivity extends AppCompatActivity {

    private ActivityBookingBinding binding;
    private int numberOfPeople = 1; // Variable to hold the count

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout using ViewBinding
        binding = ActivityBookingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set the initial count on the screen
        updatePeopleCount();

        // Set up the click listener for the increase button
        binding.btnIncreasePeople.setOnClickListener(v -> {
            numberOfPeople++;
            updatePeopleCount();
        });

        // Set up the click listener for the decrease button
        binding.btnDecreasePeople.setOnClickListener(v -> {
            if (numberOfPeople > 1) { // Prevent count from going below 1
                numberOfPeople--;
                updatePeopleCount();
            } else {
                Toast.makeText(this, "Cannot have less than 1 person", Toast.LENGTH_SHORT).show();
            }
        });

        // Add logic for the confirm booking button
        binding.btnConfirmBooking.setOnClickListener(v -> {
            // Handle the booking confirmation logic here
            Toast.makeText(this, "Booking confirmed for " + numberOfPeople + " people.", Toast.LENGTH_LONG).show();
        });
    }

    /**
     * A helper method to update the TextView with the current count.
     */
    private void updatePeopleCount() {
        binding.tvPeopleCount.setText(String.valueOf(numberOfPeople));
    }
}