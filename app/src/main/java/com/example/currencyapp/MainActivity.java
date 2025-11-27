package com.example.currencyapp;

import android.annotation.SuppressLint;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CurrencyAppLog";

    private Spinner spinnerCurrencySelection;
    private RecyclerView recyclerViewMain;
    private ProgressBar progressBarLoading;
    private TextView textViewPopular;
    private TextView textViewFavourites;

    private CurrencyAdapter currencyAdapter;

    private MainViewModel viewModel;

    private boolean showingFavourites = false;

    private List<CurrencyRatesModel> popularList;
    private List<CurrencyRateEntity> favouriteEntities;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        initViews();
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        currencyAdapter = new CurrencyAdapter();
        recyclerViewMain.setAdapter(currencyAdapter);

        currencyAdapter.setOnStarClickListener(new CurrencyAdapter.OnStarClickListener() {
            @Override
            public void onStarClick(CurrencyRatesModel model, boolean willBeFavourite) {
                if (willBeFavourite) {
                    CurrencyRateEntity entity = new CurrencyRateEntity();
                    entity.code = model.code;
                    entity.rate = model.rate;
                    viewModel.insertCurrencies(entity);
                } else {
                    viewModel.removeCurrencies(model.code);
                }
            }
        });


        viewModel.getCurrencies().observe(this, new Observer<List<CurrencyRatesModel>>() {
            @Override
            public void onChanged(List<CurrencyRatesModel> currencyRatesModels) {
                popularList = currencyRatesModels;
                updateList();
            }
        });

        viewModel.getAllFavouriteCurrencies().observe(this, new Observer<List<CurrencyRateEntity>>() {
            @Override
            public void onChanged(List<CurrencyRateEntity> currencyRateEntities) {
                favouriteEntities = currencyRateEntities;
                currencyAdapter.setFavouriteCodes(favouriteEntities);
                updateList();
            }
        });

        setupSpinnerListener();
        setupTabs();

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

    private void setupSpinnerListener() {
        spinnerCurrencySelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortingByCurrency(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void sortingByCurrency(int position) {
        String sorted;
        switch (position) {
            case 1:
                sorted = "EUR";
                break;
            case 2:
                sorted = "RUB";
                break;
            case 3:
                sorted = "CNY";
                break;
            case 4:
                sorted = "JPY";
                break;
            case 5:
                sorted = "ILS";
                break;
            case 6:
                sorted = "KZT";
                break;
            case 7:
                sorted = "AED";
                break;
            case 8:
                sorted = "BYN";
                break;
            case 9:
                sorted = "KRW";
                break;
            default:
                sorted = "USD";
        }
        viewModel.setBaseCurrency(sorted);
        viewModel.loadCurrency();
    }

    private void setupTabs() {
        textViewPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showingFavourites = false;
                updateTabsUI();
                updateList();
            }
        });

        textViewFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showingFavourites = true;
                updateTabsUI();
                updateList();
            }
        });

        showingFavourites = false;
        updateTabsUI();
    }

    private void updateTabsUI() {
        if (showingFavourites) {
            textViewFavourites.setSelected(true);
            textViewPopular.setSelected(false);
        } else {
            textViewPopular.setSelected(true);
            textViewFavourites.setSelected(false);
        }
    }

    private void updateList() {
        if (popularList == null) {
            return;
        }

        if (!showingFavourites) {
            currencyAdapter.setCurrencies(popularList);
        } else {
            if (favouriteEntities == null || favouriteEntities.isEmpty()) {
                currencyAdapter.setCurrencies(new ArrayList<CurrencyRatesModel>());
                return;
            }

            Set<String> favCodes = new HashSet<>();
            for (CurrencyRateEntity entity : favouriteEntities) {
                favCodes.add(entity.code);
            }

            List<CurrencyRatesModel> favouriteList = new ArrayList<>();
            for (CurrencyRatesModel model : popularList) {
                if (favCodes.contains(model.code)) {
                    favouriteList.add(model);
                }
            }

            currencyAdapter.setCurrencies(favouriteList);
        }
    }

    private void initViews() {
        spinnerCurrencySelection = findViewById(R.id.spinnerCurrencySelection);
        recyclerViewMain = findViewById(R.id.recyclerViewMain);
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
        int id = item.getItemId();

        if (id == R.id.menuSortCodeAsc) {
            currencyAdapter.sortByCodeAsc();
            return true;
        } else if (id == R.id.menuSortCodeDes) {
            currencyAdapter.sortByCodeDes();
            return true;
        } else if (id == R.id.menuSortRateAsc) {
            currencyAdapter.sortByRateAsc();
            return true;
        } else if (id == R.id.menuSortRateDes) {
            currencyAdapter.sortByRateDes();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}