package edu.sharif.cryptocurrency;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class Converters {
    @TypeConverter
    public static HashMap<String, Double> stringToHashMap(String value) {
        Gson gson = new GsonBuilder().create();
        Type hashMapType = new TypeToken<HashMap<String, Double>>() {}.getType();

        return gson.fromJson(value, hashMapType);
    }

    @TypeConverter
    public static String hashMapToString(HashMap<String, Double> hashMap) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(hashMap);
    }
}
