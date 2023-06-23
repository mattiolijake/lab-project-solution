package au.edu.unsw.infs3634_lab.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;

import au.edu.unsw.infs3634_lab.R;
import au.edu.unsw.infs3634_lab.api.Crypto;
import kotlin.reflect.KParameter;

public class CryptoAdapter extends RecyclerView.Adapter<CryptoAdapter.MyViewHolder> {
    private ArrayList<Crypto> localDataSet;
    private RecyclerViewClickListener localListener;

    /**
     * Implement custom ViewHolder
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView name, value, change;

        public MyViewHolder(@NonNull View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            image = itemView.findViewById(R.id.ivArt);
            name = itemView.findViewById(R.id.tvCryptoName);
            value = itemView.findViewById(R.id.tvCryptoValue);
            change = itemView.findViewById(R.id.tvCryptoChange);
            // Implement click listener event for the row
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onRowClick((String) itemView.getTag());
                    }
                }
            });
        }
    }

    /**
     * Initialize the dataset of the Adapter
     *
     * @param dataSet containing the data to populate views to be used
     * by RecyclerView
     */
    public CryptoAdapter(ArrayList<Crypto> dataSet, RecyclerViewClickListener listener) {
        localDataSet = dataSet;
        localListener = listener;

    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public CryptoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);
        return new MyViewHolder(view, localListener);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull CryptoAdapter.MyViewHolder holder, int position) {
        // Get element from the dataset at this position and replace the
        // contents of the view with that element
        Crypto crypto = localDataSet.get(position);
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        holder.name.setText(crypto.getName());
        holder.value.setText(formatter.format(Double.valueOf(crypto.getPriceUsd())));
        holder.change.setText(crypto.getPercentChange1h() + " %");
        // Set crypto symbol as a tag to the view holder
        holder.itemView.setTag(crypto.getSymbol());
    }

    // Return the size of the dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }



}
