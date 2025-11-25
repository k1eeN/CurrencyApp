package com.example.currencyapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder> {

    private List<CurrencyRatesModel> currencies = new ArrayList<>();

    public void setCurrencies(List<CurrencyRatesModel> currencies) {
        this.currencies = currencies;
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
