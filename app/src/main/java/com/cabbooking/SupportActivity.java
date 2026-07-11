package com.cabbooking;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SupportActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        findViewById(R.id.toolbar).setOnClickListener(v -> finish());

        findViewById(R.id.faqRow).setOnClickListener(v ->
            Toast.makeText(this, "FAQs coming soon", Toast.LENGTH_SHORT).show());
        findViewById(R.id.contactRow).setOnClickListener(v ->
            Toast.makeText(this, "Contact: support@cabbooking.com", Toast.LENGTH_SHORT).show());
        findViewById(R.id.reportRow).setOnClickListener(v ->
            Toast.makeText(this, "Report issue feature coming soon", Toast.LENGTH_SHORT).show());
    }
}
