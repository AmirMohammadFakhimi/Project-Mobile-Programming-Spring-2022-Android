package edu.sharif.cryptocurrency;

import android.app.Application;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CryptocurrencyRepository {
    private static final String[] cryptocurrenciesSymbol = {"BTC/USD", "ETH/USD", "XRP/USD", "USDT/USD", "ADA/USD", "XLM/USD",
            "USDC/USD", "DOGE/USD", "LINK/USD"};

    private final CryptocurrencyDao cryptocurrencyDao;
    private LiveData<ArrayList<Cryptocurrency>> cryptocurrencies;

    public CryptocurrencyRepository(@NonNull Application application) {
        CryptocurrencyRoomDatabase database =
                CryptocurrencyRoomDatabase.getInstance(application);

        cryptocurrencyDao = database.cryptocurrencyDao();

//        UNCOMMENT TO CLEAR DATA IN DATABASE
//        Thread thread = new Thread(cryptocurrencyDao::deleteTable);
//        thread.start();
//        try {
//            thread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    synchronized public LiveData<ArrayList<Cryptocurrency>> getCryptocurrenciesFromApi(View view) {
        RequestQueue queue = Volley.newRequestQueue(view.getContext());

        for (String symbol : cryptocurrenciesSymbol) {
            StringRequest cryptocurrencyRequest = getCryptocurrencyFromApi(view, symbol);
            queue.add(cryptocurrencyRequest);

            StringRequest cryptocurrencyPriceRequest = getCryptocurrencyPriceFromApi(view, symbol);
            queue.add(cryptocurrencyPriceRequest);
        }

        for (String symbol : cryptocurrenciesSymbol)
            cryptocurrencies.getValue().add(cryptocurrencyDao.getCryptocurrency(symbol).getValue());

        return cryptocurrencies;
    }

    public StringRequest getCryptocurrencyFromApi(View view, String symbol) {
        String apiUrl = "https://api.twelvedata.com/time_series?apikey=" + BuildConfig.TWELVE_DATA_API_KEY +
                "&interval=1day&symbol=" + symbol + "&dp=2&format=JSON";

        return new StringRequest(Request.Method.GET, apiUrl, response -> {
            try {
                JSONObject cryptocurrencyInfoString = new JSONObject(response);
                JSONObject cryptocurrencyMeta = cryptocurrencyInfoString.getJSONObject("meta");

                String name = cryptocurrencyMeta.getString("currency_base");

                JSONArray cryptocurrencyHistory = cryptocurrencyInfoString.getJSONArray("values");

                HashMap<String, Double> cryptocurrencyInfos = new HashMap<>();
                for (int i = 0; i < 30; i++) {
                    JSONObject cryptocurrencyHistoryItem = cryptocurrencyHistory.getJSONObject(i);
                    String date = cryptocurrencyHistoryItem.getString("datetime");
                    double close = cryptocurrencyHistoryItem.getDouble("close");

                    cryptocurrencyInfos.put(date, close);
                }

                Thread thread = new Thread(() -> {
                    boolean isCacheAvailable = cryptocurrencyDao.count(symbol) > 0;

                    if (isCacheAvailable)
                        cryptocurrencyDao.updateHistory(symbol, cryptocurrencyInfos);
                    else
                        cryptocurrencyDao.insert(new Cryptocurrency(symbol, name, cryptocurrencyInfos));

                });
                thread.join();
                thread.start();
            } catch (JSONException | InterruptedException e) {
                e.printStackTrace();
                createToast(view, "Unexpected Error!\n" + response);
            }

        },
                error -> createToast(view, "There was a problem with the connection."));
    }

    public StringRequest getCryptocurrencyPriceFromApi(View view, String symbol) {
        String apiUrl = "https://api.twelvedata.com/price?symbol=" + symbol + "&apikey=" +
                BuildConfig.TWELVE_DATA_API_KEY + "&dp=2&format=JSON";

        return new StringRequest(Request.Method.GET, apiUrl, response -> {
            try {
                JSONObject cryptocurrencyPrice = new JSONObject(response);
                double price = cryptocurrencyPrice.getDouble("price");

                Thread thread = new Thread(() -> cryptocurrencyDao.updatePrice(symbol, price));
                thread.join();
                thread.start();
            } catch (JSONException | InterruptedException e) {
                e.printStackTrace();
                createToast(view, "Unexpected Error!\n" + response);
            }

        },
                error -> createToast(view, "There was a problem with the connection."));
    }

    public LiveData<ArrayList<Cryptocurrency>> getCryptocurrencies() {
        return cryptocurrencies;
    }

    private void createToast(View view, String message) {
//        view.findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
        Toast.makeText(view.getContext(), message, Toast.LENGTH_LONG).show();
    }
}
