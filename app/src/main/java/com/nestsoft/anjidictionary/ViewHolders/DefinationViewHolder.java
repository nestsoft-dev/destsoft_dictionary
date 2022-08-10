package com.nestsoft.anjidictionary.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nestsoft.anjidictionary.R;

public class DefinationViewHolder extends RecyclerView.ViewHolder {
    public TextView textview_definations,textview_example,textview_synonys,textview_Antonyms;
    public DefinationViewHolder(@NonNull View itemView) {
        super(itemView);
        textview_definations = itemView.findViewById(R.id.textview_definations);
        textview_example = itemView.findViewById(R.id.textview_example);
        textview_synonys = itemView.findViewById(R.id.textview_synonys);
        textview_Antonyms = itemView.findViewById(R.id.textview_Antonyms);
    }
}
