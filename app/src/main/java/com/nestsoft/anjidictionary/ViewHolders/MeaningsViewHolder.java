package com.nestsoft.anjidictionary.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nestsoft.anjidictionary.R;

public class MeaningsViewHolder extends RecyclerView.ViewHolder {
  public   TextView textview_partOfSpeech;
  public   RecyclerView recycler_definations;
    public MeaningsViewHolder(@NonNull View itemView) {
        super(itemView);
        textview_partOfSpeech = itemView.findViewById(R.id.textview_partOfSpeech);
        recycler_definations = itemView.findViewById(R.id.recycler_definations);
    }
}
