package com.cabbooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class RideConfirmActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private String destination;
    private String selectedRide = "Cab";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_confirm);

        destination = getIntent().getStringExtra("destination");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.confirmMap);
        if (mapFragment != null) mapFragment.getMapAsync(this);

        setupRideOptions();
        findViewById(R.id.confirmBtn).setOnClickListener(v -> {
            Intent i = new Intent(this, LiveTrackingActivity.class);
            i.putExtra("destination", destination);
            i.putExtra("rideType", selectedRide);
            startActivity(i);
        });
    }

    private void setupRideOptions() {
        LinearLayout container = findViewById(R.id.rideOptions);
        String[][] rides = {
            {"Cab", "3 min", "₹150", "🚗"},
            {"Bike", "5 min", "₹80", "🏍️"},
            {"Auto", "7 min", "₹100", "🛺"}
        };
        for (String[] r : rides) {
            View v = getLayoutInflater().inflate(R.layout.item_ride_option, container, false);
            ((TextView) v.findViewById(R.id.optName)).setText(r[0]);
            ((TextView) v.findViewById(R.id.optTime)).setText(r[1]);
            ((TextView) v.findViewById(R.id.optFare)).setText(r[2]);
            ((TextView) v.findViewById(R.id.optIcon)).setText(r[3]);
            v.setOnClickListener(view -> {
                selectedRide = r[0];
                ((TextView) findViewById(R.id.fareText)).setText("Estimated fare: " + r[2]);
            });
            container.addView(v);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(28.6139, 77.2090), 12));
        mMap.addMarker(new MarkerOptions().position(new LatLng(28.6139, 77.2090)).title("Pickup"));
    }
}
