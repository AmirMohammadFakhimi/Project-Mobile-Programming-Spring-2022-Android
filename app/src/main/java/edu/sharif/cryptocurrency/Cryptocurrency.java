package edu.sharif.cryptocurrency;

import androidx.room.Entity;

import java.util.ArrayList;
import java.util.HashMap;

@Entity(primaryKeys = "symbol")
public class Cryptocurrency {
    private static final ArrayList<Cryptocurrency> cryptocurrencies = new ArrayList<>();

    private final String symbol;
    private final String name;
    private double price = 0;

    private boolean isFavorite = false;
    private double virtualTradingAmount = 0;
    private HashMap<String, Double> history;


    public Cryptocurrency(String symbol, String name, HashMap<String, Double> history) {
        this.symbol = symbol;
        this.name = name;
        this.history = history;

        cryptocurrencies.add(this);
    }

    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
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
