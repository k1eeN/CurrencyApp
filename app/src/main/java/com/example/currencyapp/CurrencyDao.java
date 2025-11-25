package com.example.currencyapp;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.Map;

import io.reactivex.rxjava3.core.Completable;

@Dao
public interface CurrencyDao {

    @Query("SELECT * FROM favourite_currency")
    LiveData<Map<String, Double>> getAllFavouriteCurrencies();

    @Insert
    Completable insertCurrency(CurrencyResponse currencyResponse);

    @Query("DELETE FROM favourite_currency WHERE id = :currencyId")
    Completable removeCurrency(int currencyId);
}
