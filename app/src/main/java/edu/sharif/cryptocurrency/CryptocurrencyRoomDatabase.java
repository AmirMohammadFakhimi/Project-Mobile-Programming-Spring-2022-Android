package edu.sharif.cryptocurrency;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Cryptocurrency.class}, version = 3)
public abstract class CryptocurrencyRoomDatabase extends RoomDatabase {
    public abstract CryptocurrencyDao cryptocurrencyDao();

    private static CryptocurrencyRoomDatabase instance;

    public static CryptocurrencyRoomDatabase getInstance(Context context) {
        if (instance != null)
            return instance;

        synchronized (CryptocurrencyRoomDatabase.class) {
            if (instance == null) {
                instance = Room.databaseBuilder(context, CryptocurrencyRoomDatabase.class, "cryptocurrency_database")
                        .fallbackToDestructiveMigration().build();
            }
            return instance;
        }
    }
}
