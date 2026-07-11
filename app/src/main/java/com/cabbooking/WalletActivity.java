package com.cabbooking;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class WalletActivity extends AppCompatActivity {
    private List<String[]> transactions;
    private int balance = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        findViewById(R.id.toolbar).setOnClickListener(v -> finish());

        TextView balanceText = findViewById(R.id.balanceText);
        balanceText.setText("₹ " + balance + ".00");

        transactions = new ArrayList<>();
        transactions.add(new String[]{"+", "Added via UPI", "Today, 10:00 AM", "+₹500"});
        transactions.add(new String[]{"-", "Trip to India Gate", "Today, 10:30 AM", "-₹150"});
        transactions.add(new String[]{"-", "Trip to Aerocity", "Yesterday", "-₹80"});
        transactions.add(new String[]{"+", "Cashback", "Mon, 9:00 AM", "+₹20"});

        RecyclerView list = findViewById(R.id.transactionsList);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(new RecyclerView.Adapter() {
            @NonNull @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = getLayoutInflater().inflate(R.layout.item_transaction, parent, false);
                return new RecyclerView.ViewHolder(v) {};
            }
            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                String[] t = transactions.get(position);
                ((TextView) holder.itemView.findViewById(R.id.txnIcon)).setText(t[0].equals("+") ? "📥" : "📤");
                ((TextView) holder.itemView.findViewById(R.id.txnTitle)).setText(t[1]);
                ((TextView) holder.itemView.findViewById(R.id.txnDate)).setText(t[2]);
                ((TextView) holder.itemView.findViewById(R.id.txnAmount)).setText(t[3]);
            }
            @Override public int getItemCount() { return transactions.size(); }
        });

        findViewById(R.id.addMoneyBtn).setOnClickListener(v ->
            Toast.makeText(this, "Add money feature coming soon", Toast.LENGTH_SHORT).show());
        findViewById(R.id.sendBtn).setOnClickListener(v ->
            Toast.makeText(this, "Send money feature coming soon", Toast.LENGTH_SHORT).show());
    }
}
