package com.example.currencyapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrencyResponse {

    @SerializedName("base_code")
    @Expose
    private String baseCode;

    @SerializedName("conversion_rates")
    @Expose
    private ConversionRates conversionRates;

    public CurrencyResponse(String baseCode, ConversionRates conversionRates) {
        this.baseCode = baseCode;
        this.conversionRates = conversionRates;
    }

    public String getBaseCode() {
        return baseCode;
    }

    public void setBaseCode(String baseCode) {
        this.baseCode = baseCode;
    }

    public ConversionRates getConversionRates() {
        return conversionRates;
    }

    public void setConversionRates(ConversionRates conversionRates) {
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
