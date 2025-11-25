package com.example.currencyapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class CurrencyResponse {

    @SerializedName("base_code")
    @Expose
    private String baseCode;

    @SerializedName("conversion_rates")
    public Map<String, Double> conversionRates;

    public CurrencyResponse(String baseCode, Map<String, Double> conversionRates) {
        this.baseCode = baseCode;
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

    @Override
    public String toString() {
        return "CurrencyResponse{" +
                "baseCode='" + baseCode + '\'' +
                ", conversionRates=" + conversionRates +
                '}';
    }
}
