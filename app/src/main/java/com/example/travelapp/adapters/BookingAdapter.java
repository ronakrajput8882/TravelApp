package com.example.travelapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelapp.R;
import com.example.travelapp.database.DBHelper;
import com.example.travelapp.models.Booking;
import com.example.travelapp.models.Trip;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    private Context context;
    private List<Booking> bookingList;
    private DBHelper dbHelper;

    public BookingAdapter(Context context, List<Booking> bookingList, DBHelper dbHelper) {
        this.context = context;
        this.bookingList = bookingList;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_booking, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking booking = bookingList.get(position);
        Trip bookedTrip = dbHelper.getTripById(booking.getTripId());

        if (bookedTrip != null) {
            holder.tvTripTitle.setText("Trip: " + bookedTrip.getTitle());
            holder.tvBookingDate.setText("Date: " + booking.getBookingDate());
            holder.tvNumberOfPeople.setText("People: " + booking.getNumberOfPeople());

            // Calculate total amount here using the trip's price
            double totalAmount = bookedTrip.getPrice() * booking.getNumberOfPeople();
            holder.tvTotalAmount.setText("Total: $" + String.format("%.2f", totalAmount));
        } else {
            // Handle case where trip is not found
            holder.tvTripTitle.setText("Trip: Not Found");
            holder.tvBookingDate.setText("Date: " + booking.getBookingDate());
            holder.tvNumberOfPeople.setText("People: " + booking.getNumberOfPeople());
            holder.tvTotalAmount.setText("Total: $0.00");
        }
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView tvTripTitle, tvBookingDate, tvNumberOfPeople, tvTotalAmount;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTripTitle = itemView.findViewById(R.id.tv_booking_trip_title);
            tvBookingDate = itemView.findViewById(R.id.tv_booking_date);
            tvNumberOfPeople = itemView.findViewById(R.id.tv_booking_people);
            tvTotalAmount = itemView.findViewById(R.id.tv_booking_total);
        }
    }
}