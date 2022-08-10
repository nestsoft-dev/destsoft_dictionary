package com.nestsoft.anjidictionary.ViewHolders;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nestsoft.anjidictionary.R;

public class PhoneticsViewHolder extends RecyclerView.ViewHolder {
    public TextView textview_phonetics;
    public ImageButton imageButton_audio;
    public PhoneticsViewHolder(@NonNull View itemView) {
        super(itemView);

        textview_phonetics = itemView.findViewById(R.id.textview_phonetics);
        imageButton_audio = itemView.findViewById(R.id.imageButton_audio);
    }
}
