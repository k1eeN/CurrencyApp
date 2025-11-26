package com.example.currencyapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder> {

    private List<CurrencyRatesModel> currencies = new ArrayList<>();

    public void setCurrencies(List<CurrencyRatesModel> currencies) {
        this.currencies = currencies;
        notifyDataSetChanged();
    }

    public void sortByCodeAsc() {
        Collections.sort(currencies, new Comparator<CurrencyRatesModel>() {
            @Override
            public int compare(CurrencyRatesModel o1, CurrencyRatesModel o2) {
                return o1.code.compareToIgnoreCase(o2.code);
            }
        });
        notifyDataSetChanged();
    }

    public void sortByCodeDes() {
        Collections.sort(currencies, new Comparator<CurrencyRatesModel>() {
            @Override
            public int compare(CurrencyRatesModel o1, CurrencyRatesModel o2) {
                return o2.code.compareToIgnoreCase(o1.code);
            }
        });
        notifyDataSetChanged();
    }

    public void sortByRateAsc() {
        Collections.sort(currencies, new Comparator<CurrencyRatesModel>() {
            @Override
            public int compare(CurrencyRatesModel o1, CurrencyRatesModel o2) {
                return Double.compare(o1.rate, o2.rate);
            }
        });
        notifyDataSetChanged();
    }

    public void sortByRateDes() {
        Collections.sort(currencies, new Comparator<CurrencyRatesModel>() {
            @Override
            public int compare(CurrencyRatesModel o1, CurrencyRatesModel o2) {
                return Double.compare(o2.rate, o1.rate);
            }
        });
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.currency_item,
                parent,
                false
        );
        return new CurrencyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder holder, int position) {
        CurrencyRatesModel currencyRatesModel = currencies.get(position);
        holder.textViewCurrency.setText(currencyRatesModel.code + " - " + currencyRatesModel.rate);
    }

    @Override
    public int getItemCount() {
        return currencies.size();
    }

    static class CurrencyViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewCurrency;

        public CurrencyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCurrency = itemView.findViewById(R.id.textViewCurrency);
        }
    }
}
