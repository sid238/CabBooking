package com.cabbooking;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LiveTrackingActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private TextView tripStatus, tripEta, otpDisplay;
    private Button callBtn, cancelBtn;
    private int step = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_tracking);

        tripStatus = findViewById(R.id.tripStatus);
        tripEta = findViewById(R.id.tripEta);
        otpDisplay = findViewById(R.id.otpDisplay);
        callBtn = findViewById(R.id.callBtn);
        cancelBtn = findViewById(R.id.cancelBtn);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.trackMap);
        if (mapFragment != null) mapFragment.getMapAsync(this);

        callBtn.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_DIAL);
            i.setData(android.net.Uri.parse("tel:+919876543210"));
            startActivity(i);
        });

        cancelBtn.setOnClickListener(v -> {
            finish();
        });

        simulateTrip();
    }

    private void simulateTrip() {
        new Handler().postDelayed(() -> {
            tripStatus.setText("Driver is arriving");
            tripEta.setText("Arriving in 3 min");
        }, 2000);
        new Handler().postDelayed(() -> {
            tripStatus.setText("Driver has arrived");
            tripEta.setText("Please come to pickup point");
        }, 6000);
        new Handler().postDelayed(() -> {
            tripStatus.setText("Trip started");
            tripEta.setText("Heading to destination");
        }, 10000);
        new Handler().postDelayed(() -> {
            tripStatus.setText("Trip completed");
            tripEta.setText("You have arrived!");
            startActivity(new Intent(this, RatingActivity.class));
        }, 16000);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(28.6139, 77.2090), 12));
    }
}
