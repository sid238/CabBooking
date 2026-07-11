package com.cabbooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RatingActivity extends AppCompatActivity {
    private int rating = 0;
    private TextView[] stars = new TextView[5];
    private EditText feedbackInput;
    private Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        stars[0] = findViewById(R.id.star1);
        stars[1] = findViewById(R.id.star2);
        stars[2] = findViewById(R.id.star3);
        stars[3] = findViewById(R.id.star4);
        stars[4] = findViewById(R.id.star5);
        feedbackInput = findViewById(R.id.feedbackInput);
        submitBtn = findViewById(R.id.submitBtn);

        for (int i = 0; i < 5; i++) {
            final int index = i;
            stars[i].setOnClickListener(v -> setRating(index + 1));
        }

        submitBtn.setOnClickListener(v -> {
            Toast.makeText(this, "Thank you for your feedback!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        });

        findViewById(R.id.skipBtn).setOnClickListener(v -> {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        });
    }

    private void setRating(int r) {
        rating = r;
        for (int i = 0; i < 5; i++) {
            stars[i].setText(i < r ? "\u2605" : "\u2606");
        }
    }
}
