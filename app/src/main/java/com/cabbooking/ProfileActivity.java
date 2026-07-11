package com.cabbooking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        findViewById(R.id.toolbar).setOnClickListener(v -> finish());

        SharedPreferences prefs = getSharedPreferences("cabbooking", MODE_PRIVATE);
        String name = prefs.getString("name", "John Doe");
        String phone = prefs.getString("phone", "+91 98765 43210");

        ((TextView) findViewById(R.id.userName)).setText(name);
        ((TextView) findViewById(R.id.userPhone)).setText(phone);

        findViewById(R.id.walletRow).setOnClickListener(v ->
            startActivity(new Intent(this, WalletActivity.class)));
        findViewById(R.id.paymentsRow).setOnClickListener(v ->
            startActivity(new Intent(this, UpiPaymentActivity.class)));
        findViewById(R.id.savedPlacesRow).setOnClickListener(v ->
            Toast.makeText(this, "Saved places coming soon", Toast.LENGTH_SHORT).show());
        findViewById(R.id.supportRow).setOnClickListener(v ->
            startActivity(new Intent(this, SupportActivity.class)));
        findViewById(R.id.logoutRow).setOnClickListener(v -> {
            getSharedPreferences("cabbooking", MODE_PRIVATE).edit().clear().apply();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
