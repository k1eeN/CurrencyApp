package com.example.currencyapp;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = "CurrencyAppLog";

    private final MutableLiveData<List<CurrencyRatesModel>> currencies = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);


    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private String baseCurrency = "USD";

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<CurrencyRatesModel>> getCurrencies() {
        return currencies;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }


    public void loadCurrency() {
        Boolean loading = isLoading.getValue();
        if (loading != null && loading) {
            return;
        }
        Disposable disposable = ApiFactory.apiService.loadCurrency(baseCurrency)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Throwable {
                        isLoading.setValue(true);
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Throwable {
                        isLoading.setValue(false);
                    }
                }).map(new Function<CurrencyResponse, List<CurrencyRatesModel>>() {
                    @Override
                    public List<CurrencyRatesModel> apply(CurrencyResponse currencyResponse) throws Throwable {
                        List<CurrencyRatesModel> currencyRatesModels = new ArrayList<>();
                        for (Map.Entry<String, Double> entry : currencyResponse.conversionRates.entrySet()) {
                            CurrencyRatesModel currencyRatesModel = new CurrencyRatesModel();
                            currencyRatesModel.code = entry.getKey();
                            currencyRatesModel.rate = entry.getValue();
                            currencyRatesModels.add(currencyRatesModel);
                        }
                        return currencyRatesModels;
                    }
                }).subscribe(new Consumer<List<CurrencyRatesModel>>() {
                    @Override
                    public void accept(List<CurrencyRatesModel> currencyRatesModels) throws Throwable {
                        currencies.setValue(currencyRatesModels);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, throwable.toString());
                    }
                });
        compositeDisposable.add(disposable);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
