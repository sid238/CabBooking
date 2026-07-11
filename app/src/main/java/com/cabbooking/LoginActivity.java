package com.cabbooking;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText phoneInput;
    private Button continueBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phoneInput = findViewById(R.id.phoneInput);
        continueBtn = findViewById(R.id.continueBtn);

        phoneInput.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) {
                continueBtn.setEnabled(s.length() == 10);
            }
        });

        continueBtn.setOnClickListener(v -> {
            String phone = phoneInput.getText().toString();
            if (phone.length() == 10) {
                Intent intent = new Intent(this, OTPActivity.class);
                intent.putExtra("phone", phone);
                startActivity(intent);
            }
        });
    }
}
