package com.cabbooking;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class OTPActivity extends AppCompatActivity {
    private EditText otpInput;
    private Button verifyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        String phone = getIntent().getStringExtra("phone");
        TextView phoneDisplay = findViewById(R.id.phoneDisplay);
        phoneDisplay.setText("OTP sent to +91 " + phone);

        otpInput = findViewById(R.id.otpInput);
        verifyBtn = findViewById(R.id.verifyBtn);

        verifyBtn.setOnClickListener(v -> {
            String otp = otpInput.getText().toString();
            if (otp.length() == 6) {
                startActivity(new Intent(this, SetupProfileActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Enter 6-digit OTP", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
