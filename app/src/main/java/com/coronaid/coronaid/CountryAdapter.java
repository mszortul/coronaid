package com.coronaid.coronaid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;


public class CountryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<CountryData> countryDataArrayList;
    private RecyclerViewClickListener recyclerViewClickListener;

    CountryAdapter(Context context, ArrayList<CountryData> countryDataArrayList, RecyclerViewClickListener listener) {
        this.context = context;
        this.countryDataArrayList = countryDataArrayList;
        this.recyclerViewClickListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_feed, parent, false);
        return new CountryDataViewHolder(view, recyclerViewClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final CountryData countryData = countryDataArrayList.get(position);

        if (holder instanceof CountryDataViewHolder) {
            final CountryDataViewHolder rowViewHolder = (CountryDataViewHolder) holder;

            String tc, nc, td, nd;

            tc = countryData.getTotalCase();
            nc = countryData.getNewCase();
            td = countryData.getTotalDeath();
            nd = countryData.getNewDeath();

            rowViewHolder.name.setText(countryData.getName());
            rowViewHolder.tCase.setText(tc);
            rowViewHolder.nCase.setText(nc);
            rowViewHolder.tDeath.setText(td);
            rowViewHolder.nDeath.setText(nd);
            rowViewHolder.flag.setImageDrawable(ContextCompat.getDrawable(context.getApplicationContext(), countryData.getFlagId()));
        }
    }

    @Override
    public int getItemCount() {
        return countryDataArrayList.size();
    }

    public static class CountryDataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tCase, tDeath, nCase, nDeath, name;
        ImageView flag;

        RecyclerViewClickListener clickListener;

        CountryDataViewHolder(@NonNull View view, RecyclerViewClickListener listener) {
            super(view);

            clickListener = listener;

            flag = view.findViewById(R.id.countryFlag);
            name = view.findViewById(R.id.countryName);
            tCase = view.findViewById(R.id.countryTCase);
            nCase = view.findViewById(R.id.countryNCase);
            tDeath = view.findViewById(R.id.countryTDeath);
            nDeath = view.findViewById(R.id.countryNDeath);

            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition());
        }
    }
}
