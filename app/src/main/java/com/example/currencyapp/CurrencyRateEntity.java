package com.example.currencyapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "currency_rates")
public class CurrencyRateEntity {

    @PrimaryKey
    @NotNull
    public String code;

    public  double rate;
}
