package edu.sharif.cryptocurrency;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

public class CryptocurrencyViewModel extends AndroidViewModel {
    private final CryptocurrencyRepository cryptocurrencyRepository;
    private LiveData<List<Cryptocurrency>> cryptocurrencies;

    public CryptocurrencyViewModel(@NonNull Application application) {
        super(application);

        cryptocurrencyRepository = new CryptocurrencyRepository(application);
        cryptocurrencies = cryptocurrencyRepository.getCryptocurrencies();
    }

    public LiveData<List<Cryptocurrency>> getCryptocurrencies(View view) {
        cryptocurrencies = cryptocurrencyRepository.getCryptocurrenciesFromApi(view);
        return cryptocurrencies;
    }

    public void updateFavorite(String symbol, boolean isFavorite) {
        cryptocurrencyRepository.updateFavorite(symbol, isFavorite);
    }
}
