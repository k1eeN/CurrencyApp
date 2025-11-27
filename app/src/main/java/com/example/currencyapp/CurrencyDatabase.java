package com.example.currencyapp;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {CurrencyRateEntity.class}, version = 1, exportSchema = false)
public abstract class CurrencyDatabase extends RoomDatabase {


    private static final String DB_NAME = "currency.db";
    private static CurrencyDatabase instance = null;

    public static CurrencyDatabase getInstance(Application application) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    application,
                    CurrencyDatabase.class,
                    DB_NAME
            ).build();
        }
        return instance;
    }

    abstract CurrencyDao currencyDao();

}
