package com.cabbooking;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
            R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.map);
        if (mapFragment != null) mapFragment.getMapAsync(this);

        setupServiceTiles();
        setupSavedPlaces();
        setupNavigationDrawer();
        requestLocationPermission();
    }

    private void setupServiceTiles() {
        LinearLayout container = findViewById(R.id.serviceTiles);
        String[][] services = {
            {"Cab", "4 seats", "🚗"},
            {"Bike", "1 seat", "🏍️"},
            {"Auto", "3 seats", "🛺"},
            {"Parcel", "Delivery", "📦"}
        };
        for (String[] s : services) {
            View v = getLayoutInflater().inflate(R.layout.item_service_tile, container, false);
            ((TextView) v.findViewById(R.id.tileName)).setText(s[0]);
            ((TextView) v.findViewById(R.id.tileSub)).setText(s[1]);
            ((TextView) v.findViewById(R.id.tileIcon)).setText(s[2]);
            v.setOnClickListener(view -> {
                Intent i = new Intent(this, SearchActivity.class);
                i.putExtra("service", s[0]);
                startActivity(i);
            });
            container.addView(v);
        }
    }

    private void setupSavedPlaces() {
        LinearLayout container = findViewById(R.id.savedPlacesRow);
        String[][] places = {{"Home", "123 Main Street"}, {"Work", "456 Office Road"}};
        for (String[] p : places) {
            View v = getLayoutInflater().inflate(R.layout.item_saved_place, container, false);
            ((TextView) v.findViewById(R.id.placeLabel)).setText(p[0]);
            ((TextView) v.findViewById(R.id.placeAddress)).setText(p[1]);
            v.setOnClickListener(view -> {
                Intent i = new Intent(this, SearchActivity.class);
                i.putExtra("destination", p[1]);
                startActivity(i);
            });
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
            getCurrentLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST && grantResults.length > 0
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        }
    }

    private void getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) return;
        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null && mMap != null) {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(latLng).title("You are here"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        getCurrentLocation();
    }
}
