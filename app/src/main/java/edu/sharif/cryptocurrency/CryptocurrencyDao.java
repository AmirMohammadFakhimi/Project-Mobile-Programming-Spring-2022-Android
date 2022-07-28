package edu.sharif.cryptocurrency;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.HashMap;

public interface CryptocurrencyDao {
    @Insert
    void insert(Cryptocurrency cryptocurrency);

    @Query("UPDATE cryptocurrency SET isFavorite = :isFavorite WHERE symbol = :symbol")
    void updateIsFavorite(String symbol, boolean isFavorite);

    @Query("UPDATE cryptocurrency SET virtualTradingAmount = :virtualTradingAmount WHERE symbol = :symbol")
    void updateVirtualTradingAmount(String symbol, double virtualTradingAmount);

    @Query("UPDATE cryptocurrency SET history = :history WHERE symbol = :symbol")
    void updateHistory(String symbol, HashMap<String, Double> history);

    @Query("UPDATE cryptocurrency SET price = :price WHERE symbol = :symbol")
    void updatePrice(String symbol, double price);

    @Query("SELECT * FROM cryptocurrency WHERE symbol = :symbol")
    LiveData<Cryptocurrency> getCryptocurrency(String symbol);

    @Query("DELETE FROM cryptocurrency")
    void deleteTable();

    @Query("SELECT COUNT() FROM cryptocurrency WHERE symbol = :symbol")
    int count(String symbol);
}
