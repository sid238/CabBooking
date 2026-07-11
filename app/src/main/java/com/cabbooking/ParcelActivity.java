package com.cabbooking;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ParcelActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcel);

        findViewById(R.id.toolbar).setOnClickListener(v -> finish());

        EditText pickup = findViewById(R.id.pickupInput);
        EditText drop = findViewById(R.id.dropInput);
        EditText weight = findViewById(R.id.weightInput);
        EditText desc = findViewById(R.id.descriptionInput);
        Button requestBtn = findViewById(R.id.requestBtn);

        requestBtn.setOnClickListener(v -> {
            Toast.makeText(this, "Parcel pickup requested!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
