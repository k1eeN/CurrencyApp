package com.example.currencyapp;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = "CurrencyAppLog";

    private final MutableLiveData<ConversionRates> currencies = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private String baseCurrency = "USD";

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ConversionRates> getCurrencies() {
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
                }).subscribe(new Consumer<CurrencyResponse>() {
                    @Override
                    public void accept(CurrencyResponse currencyResponse) throws Throwable {
                        currencies.setValue(currencyResponse.getConversionRates());
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
