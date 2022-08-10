package com.nestsoft.anjidictionary.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nestsoft.anjidictionary.Models.Definations;
import com.nestsoft.anjidictionary.R;
import com.nestsoft.anjidictionary.ViewHolders.DefinationViewHolder;

import java.util.List;

public class DefinationAdapter extends RecyclerView.Adapter<DefinationViewHolder> {
    private Context context;
    private List<Definations> definationsList;

    public DefinationAdapter(Context context, List<Definations> definationsList) {
        this.context = context;
        this.definationsList = definationsList;
    }

    @NonNull
    @Override
    public DefinationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DefinationViewHolder(LayoutInflater.from(context).inflate(R.layout.definations_list_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DefinationViewHolder holder, int position) {

        holder.textview_definations.setText("Defination: "+definationsList.get(position).getDefinition());
        holder.textview_example.setText("Example: "+definationsList.get(position).getExample());
        StringBuilder synonyms = new StringBuilder();
        StringBuilder antonyms = new StringBuilder();

        synonyms.append(definationsList.get(position).getSynonyms());
        antonyms.append(definationsList.get(position).getAntonyms());

        holder.textview_synonys.setText(synonyms);
        holder.textview_Antonyms.setText(antonyms);

        holder.textview_synonys.setSelected(true);
        holder.textview_Antonyms.setSelected(true);

    }

    @Override
    public int getItemCount() {
        return definationsList.size();
    }
}
