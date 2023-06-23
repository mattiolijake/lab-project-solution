package au.edu.unsw.infs3634_lab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import au.edu.unsw.infs3634_lab.adapters.CryptoAdapter;
import au.edu.unsw.infs3634_lab.adapters.RecyclerViewClickListener;
import au.edu.unsw.infs3634_lab.api.Crypto;

public class MainActivity extends AppCompatActivity implements RecyclerViewClickListener {
    public final static String TAG = "Main-Activity";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CryptoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the handle to the recycler view
        recyclerView = findViewById(R.id.rvList);

        // Instantiate a linear recycler view layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Create an adapter instance with the list of cryptos
        adapter = new CryptoAdapter(Crypto.getCryptoCurrencies(), this);

        // Connect the adapter to the recycler view
        recyclerView.setAdapter(adapter);
    }

    // Called when user taps launch button
    public void launchDetailActivity(String msg) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(DetailActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    @Override
    public void onRowClick(String symbol) {
        launchDetailActivity(symbol);
    }
}