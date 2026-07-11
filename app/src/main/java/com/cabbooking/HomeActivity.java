package com.cabbooking;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback {
    private DrawerLayout drawerLayout;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST = 100;

    private TextView addressText;
    private LinearLayout chipBike, chipAuto, chipCab;
    private Button bookBtn;
    private String selectedService = "Bike";

    private final String[][] SERVICES = {
        {"Bike", "₹49"},
        {"Auto", "₹89"},
        {"Cab", "₹129"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawerLayout);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        ImageButton menuBtn = findViewById(R.id.menuBtn);
        ImageButton profileBtn = findViewById(R.id.profileBtn);
        addressText = findViewById(R.id.addressText);
        LinearLayout searchCard = findViewById(R.id.searchCard);
        chipBike = findViewById(R.id.chipBike);
        chipAuto = findViewById(R.id.chipAuto);
        chipCab = findViewById(R.id.chipCab);
        bookBtn = findViewById(R.id.bookBtn);
        LinearLayout savedPlacesRow = findViewById(R.id.savedPlacesRow);

        menuBtn.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
        profileBtn.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));

        searchCard.setOnClickListener(v -> openSearch(null));
        bookBtn.setOnClickListener(v -> openSearch(null));

        chipBike.setOnClickListener(v -> selectService("Bike"));
        chipAuto.setOnClickListener(v -> selectService("Auto"));
        chipCab.setOnClickListener(v -> selectService("Cab"));

        setupSavedPlaces(savedPlacesRow);
        setupNavigationDrawer();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.map);
        if (mapFragment != null) mapFragment.getMapAsync(this);

        requestLocationPermission();
    }

    private void openSearch(String destination) {
        Intent i = new Intent(this, SearchActivity.class);
        i.putExtra("service", selectedService);
        if (destination != null) i.putExtra("destination", destination);
        startActivity(i);
    }

    private void selectService(String service) {
        selectedService = service;
        chipBike.setBackgroundResource(service.equals("Bike") ? R.drawable.bg_chip_selected : R.drawable.bg_chip);
        chipAuto.setBackgroundResource(service.equals("Auto") ? R.drawable.bg_chip_selected : R.drawable.bg_chip);
        chipCab.setBackgroundResource(service.equals("Cab") ? R.drawable.bg_chip_selected : R.drawable.bg_chip);
        bookBtn.setText("Book " + service);
    }

    private void setupSavedPlaces(LinearLayout container) {
        String[][] places = {{"Home", "123 Main Street"}, {"Work", "456 Office Road"}};
        for (String[] p : places) {
            View v = getLayoutInflater().inflate(R.layout.item_saved_place, container, false);
            ((TextView) v.findViewById(R.id.placeLabel)).setText(p[0]);
            ((TextView) v.findViewById(R.id.placeAddress)).setText(p[1]);
            v.setOnClickListener(view -> openSearch(p[1]));
            container.addView(v);
        }
    }

    private void setupNavigationDrawer() {
        NavigationView navView = findViewById(R.id.navView);
        navView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_profile) startActivity(new Intent(this, ProfileActivity.class));
            else if (id == R.id.nav_history) startActivity(new Intent(this, HistoryActivity.class));
            else if (id == R.id.nav_wallet) startActivity(new Intent(this, WalletActivity.class));
            else if (id == R.id.nav_payments) startActivity(new Intent(this, UpiPaymentActivity.class));
            else if (id == R.id.nav_parcel) startActivity(new Intent(this, ParcelActivity.class));
            else if (id == R.id.nav_support) startActivity(new Intent(this, SupportActivity.class));
            else if (id == R.id.nav_about) startActivity(new Intent(this, AboutActivity.class));
            drawerLayout.closeDrawers();
            return true;
        });
    }

    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                LOCATION_PERMISSION_REQUEST);
        } else {
            loadCurrentLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST && grantResults.length > 0
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadCurrentLocation();
        } else {
            Toast.makeText(this, "Location permission needed to show your position", Toast.LENGTH_LONG).show();
        }
    }

    private void loadCurrentLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) return;

        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener(location -> {
                if (location != null) {
                    updateMapLocation(location);
                } else {
                    fusedLocationClient.getLastLocation().addOnSuccessListener(this::updateMapLocation);
                }
            })
            .addOnFailureListener(e -> fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this::updateMapLocation));
    }

    private void updateMapLocation(Location location) {
        if (location == null) return;
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        if (mMap != null) {
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(latLng).title("You are here"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        }
        if (addressText != null) addressText.setText("Current Location");
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
        loadCurrentLocation();
    }
}
