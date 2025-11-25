package com.example.currencyapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;


@Entity(tableName = "favourite_currency")
public class CurrencyResponse {

    @SerializedName("base_code")
    @Expose
    private String baseCode;

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("conversion_rates")
    public Map<String, Double> conversionRates;

    public CurrencyResponse(String baseCode, int id, Map<String, Double> conversionRates) {
        this.baseCode = baseCode;
        this.id = id;
        this.conversionRates = conversionRates;
    }

    public String getBaseCode() {
        return baseCode;
    }

    public void setBaseCode(String baseCode) {
        this.baseCode = baseCode;
    }

    public Map<String, Double> getConversionRates() {
        return conversionRates;
    }

    public void setConversionRates(Map<String, Double> conversionRates) {
        this.conversionRates = conversionRates;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CurrencyResponse{" +
                "baseCode='" + baseCode + '\'' +
                ", id=" + id +
                ", conversionRates=" + conversionRates +
                '}';
    }
}
