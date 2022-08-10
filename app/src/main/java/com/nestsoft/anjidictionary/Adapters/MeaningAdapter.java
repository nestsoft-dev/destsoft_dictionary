package com.nestsoft.anjidictionary.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nestsoft.anjidictionary.Models.Meanings;
import com.nestsoft.anjidictionary.R;
import com.nestsoft.anjidictionary.ViewHolders.MeaningsViewHolder;

import java.util.List;

public class MeaningAdapter extends RecyclerView.Adapter<MeaningsViewHolder> {
    private Context context;
    protected List<Meanings> meanings;

    public MeaningAdapter(Context context, List<Meanings> meanings) {
        this.context = context;
        this.meanings = meanings;
    }

    @NonNull
    @Override
    public MeaningsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MeaningsViewHolder(LayoutInflater.from(context).inflate(R.layout.meaning_list_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MeaningsViewHolder holder, int position) {

        holder.textview_partOfSpeech.setText("Parts of speech: "+meanings.get(position).getPartOfSpeech());
        holder.recycler_definations.setHasFixedSize(true);
        holder.recycler_definations.setLayoutManager(new GridLayoutManager(context,1));
        DefinationAdapter definationAdapter = new DefinationAdapter(context,meanings.get(position).getDefinations());
        holder.recycler_definations.setAdapter(definationAdapter);


    }

    @Override
    public int getItemCount() {
        return meanings.size();
    }
}
