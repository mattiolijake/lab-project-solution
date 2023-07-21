package au.edu.unsw.infs3634_lab.api;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {
    // First 100 coins
    // Example: https://api.coinlore.net/api/tickers
    @GET("api/tickers")
    Call<Response> getCoins();

    // Get information for specific coin
    // example: https://api.coinlore.net/api/ticker/?id=90
    @GET("api/ticker/")
    Call<ArrayList<Crypto>> getCoin(@Query("id") Integer id);
}
