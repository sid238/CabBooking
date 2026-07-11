package com.cabbooking;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private List<String[]> trips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        findViewById(R.id.toolbar).setOnClickListener(v -> finish());

        trips = new ArrayList<>();
        trips.add(new String[]{"Today, 10:30 AM", "₹150", "Connaught Place", "India Gate", "Cab", "Completed"});
        trips.add(new String[]{"Yesterday, 2:15 PM", "₹80", "Sector 62", "Aerocity", "Bike", "Completed"});
        trips.add(new String[]{"Mon, 6:00 PM", "₹200", "MG Road", "Chandni Chowk", "Cab", "Completed"});
        trips.add(new String[]{"Sun, 9:00 AM", "₹100", "Home", "Airport", "Auto", "Completed"});

        androidx.recyclerview.widget.RecyclerView list = findViewById(R.id.historyList);
        list.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this));
        list.setAdapter(new androidx.recyclerview.widget.RecyclerView.Adapter() {
            @NonNull @Override
            public androidx.recyclerview.widget.RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = getLayoutInflater().inflate(R.layout.item_trip, parent, false);
                return new androidx.recyclerview.widget.RecyclerView.ViewHolder(v) {};
            }
            @Override
            public void onBindViewHolder(@NonNull androidx.recyclerview.widget.RecyclerView.ViewHolder holder, int position) {
                String[] t = trips.get(position);
                ((TextView) holder.itemView.findViewById(R.id.tripDate)).setText(t[0]);
                ((TextView) holder.itemView.findViewById(R.id.tripFare)).setText(t[1]);
                ((TextView) holder.itemView.findViewById(R.id.tripFrom)).setText(t[2]);
                ((TextView) holder.itemView.findViewById(R.id.tripTo)).setText(t[3]);
                ((TextView) holder.itemView.findViewById(R.id.tripType)).setText(t[4]);
                ((TextView) holder.itemView.findViewById(R.id.tripStatus)).setText(t[5]);
            }
            @Override public int getItemCount() { return trips.size(); }
        });
    }
}
