package com.example.currencyapp;

import android.annotation.SuppressLint;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CurrencyAppLog";

    private Spinner spinnerCurrencySelection;
    private RecyclerView recyclerViewMain;
    private RecyclerView recyclerViewFavourite;
    private ProgressBar progressBarLoading;
    private TextView textViewPopular;
    private TextView textViewFavourites;

    private CurrencyAdapter currencyAdapter;

    private MainViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        initViews();
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        currencyAdapter = new CurrencyAdapter();
        recyclerViewMain.setAdapter(currencyAdapter);

        viewModel.getCurrencies().observe(this, new Observer<List<CurrencyRatesModel>>() {
            @Override
            public void onChanged(List<CurrencyRatesModel> currencyRatesModels) {
                currencyAdapter.setCurrencies(currencyRatesModels);
            }
        });
        viewModel.loadCurrency();

        viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading) {
                    progressBarLoading.setVisibility(View.VISIBLE);
                } else {
                    progressBarLoading.setVisibility(View.GONE);
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initViews() {
        spinnerCurrencySelection = findViewById(R.id.spinnerCurrencySelection);
        recyclerViewMain = findViewById(R.id.recyclerViewMain);
        recyclerViewFavourite = findViewById(R.id.recyclerViewFavourite);
        progressBarLoading = findViewById(R.id.progressBarLoading);
        textViewPopular = findViewById(R.id.textViewPopular);
        textViewFavourites = findViewById(R.id.textViewFavourites);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}