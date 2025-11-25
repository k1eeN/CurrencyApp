package com.example.currencyapp;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.Map;

import io.reactivex.rxjava3.core.Completable;

@Dao
public interface CurrencyDao {

    @Query("SELECT * FROM currency_rates")
    LiveData<CurrencyRateEntity> getAllFavouriteCurrencies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertCurrency(CurrencyResponse currencyResponse);

    @Query("DELETE FROM currency_rates WHERE code = :currencyCode")
    Completable removeCurrency(String currencyCode);
}
