package com.cabbooking;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UpiPaymentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upi_payment);

        findViewById(R.id.toolbar).setOnClickListener(v -> finish());

        EditText upiInput = findViewById(R.id.upiIdInput);
        Button payBtn = findViewById(R.id.payBtn);

        payBtn.setOnClickListener(v -> {
            String upiId = upiInput.getText().toString().trim();
            if (upiId.isEmpty()) {
                Toast.makeText(this, "Enter UPI ID", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Payment initiated via " + upiId, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
