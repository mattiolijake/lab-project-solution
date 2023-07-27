package au.edu.unsw.infs3634_lab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import au.edu.unsw.infs3634_lab.adapters.CryptoAdapter;
import au.edu.unsw.infs3634_lab.adapters.RecyclerViewClickListener;
import au.edu.unsw.infs3634_lab.api.Crypto;
import au.edu.unsw.infs3634_lab.api.Response;
import au.edu.unsw.infs3634_lab.api.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

        // Create an adapter instance with an empty ArrayList of Coin objects
        adapter = new CryptoAdapter(new ArrayList<Crypto>(), this);

        // Implement Retrofit to make API call
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.coinlore.net") // Set the base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create object for the service interface
        Service service = retrofit.create(Service.class);
        Call<Response> responseCall = service.getCoins();

        responseCall.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Log.d(TAG, "API call successful!");
                List<Crypto> coins = response.body().getData();
                // Supply data to the adapter to be displayed
                adapter.setData((List)coins);
                adapter.sortList(CryptoAdapter.SORT_BY_VALUE);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.d(TAG, "API Call Failure." + " URL="+call.request().url().toString());
                t.printStackTrace();
            }
        });

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
    public void onRowClick(String id) {
        launchDetailActivity(id);
    }

    // Instantiate the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        // Find the handle to search menu item
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

    // React to user interaction with the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sortName:
                adapter.sortList(CryptoAdapter.SORT_BY_NAME);
                return true;
            case R.id.sortValue:
                adapter.sortList(CryptoAdapter.SORT_BY_VALUE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}