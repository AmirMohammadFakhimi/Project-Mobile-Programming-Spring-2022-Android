package edu.sharif.cryptocurrency;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import java.util.ArrayList;
import java.util.HashMap;

@Entity(primaryKeys = "symbol")
public class Cryptocurrency {
    private static ArrayList<Cryptocurrency> cryptocurrencies = new ArrayList<>();

    @NonNull
    private final String symbol;
    private final String name;
    private double price = 0;

    private boolean isFavorite = false;
    private double virtualTradingAmount = 0;
    private HashMap<String, Double> history;

    public Cryptocurrency(@NonNull String symbol, String name, HashMap<String, Double> history) {
        this.symbol = symbol;
        this.name = name;
        this.history = history;

        cryptocurrencies.add(this);
    }

    public static ArrayList<Cryptocurrency> getCryptocurrencies() {
        return cryptocurrencies;
    }

    public boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public double getVirtualTradingAmount() {
        return virtualTradingAmount;
    }

    public void setVirtualTradingAmount(double virtualTradingAmount) {
        this.virtualTradingAmount = virtualTradingAmount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public HashMap<String, Double> getHistory() {
        return history;
    }

    public void setHistory(HashMap<String, Double> history) {
        this.history = history;
    }
}
