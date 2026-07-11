package com.cabbooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private EditText searchInput;
    private RecyclerView suggestionsList;
    private List<String[]> suggestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        String service = getIntent().getStringExtra("service");
        String dest = getIntent().getStringExtra("destination");

        findViewById(R.id.backBtn).setOnClickListener(v -> finish());

        searchInput = findViewById(R.id.searchInput);
        if (dest != null) searchInput.setText(dest);

        suggestionsList = findViewById(R.id.suggestionsList);
        suggestionsList.setLayoutManager(new LinearLayoutManager(this));

        suggestions = new java.util.ArrayList<>();
        suggestions.add(new String[]{"Connaught Place", "New Delhi, Delhi"});
        suggestions.add(new String[]{"India Gate", "New Delhi, Delhi"});
        suggestions.add(new String[]{"Chandni Chowk", "Old Delhi, Delhi"});
        suggestions.add(new String[]{"Aerocity", "New Delhi, Delhi"});
        suggestions.add(new String[]{"Sector 62, Noida", "Uttar Pradesh"});
        suggestions.add(new String[]{"MG Road", "Gurugram, Haryana"});

        suggestionsList.setAdapter(new androidx.recyclerview.widget.RecyclerView.Adapter() {
            @NonNull @Override
            public androidx.recyclerview.widget.RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = getLayoutInflater().inflate(R.layout.item_suggestion, parent, false);
                return new androidx.recyclerview.widget.RecyclerView.ViewHolder(v) {};
            }
            @Override
            public void onBindViewHolder(@NonNull androidx.recyclerview.widget.RecyclerView.ViewHolder holder, int position) {
                String[] s = suggestions.get(position);
                ((TextView) holder.itemView.findViewById(R.id.placeName)).setText(s[0]);
                ((TextView) holder.itemView.findViewById(R.id.placeAddress)).setText(s[1]);
                holder.itemView.setOnClickListener(v -> {
                    Intent i = new Intent(SearchActivity.this, RideConfirmActivity.class);
                    i.putExtra("destination", s[0]);
                    startActivity(i);
                });
            }
            @Override public int getItemCount() { return suggestions.size(); }
        });
    }
}
