package com.example.currencyapp;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("latest/{currency}")
    Single<CurrencyResponse> loadCurrency(@Path("currency") String currency);
}
