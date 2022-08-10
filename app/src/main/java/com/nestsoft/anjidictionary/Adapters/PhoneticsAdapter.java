package com.nestsoft.anjidictionary.Adapters;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nestsoft.anjidictionary.Models.Phonetics;
import com.nestsoft.anjidictionary.R;
import com.nestsoft.anjidictionary.ViewHolders.PhoneticsViewHolder;

import java.util.List;

public class PhoneticsAdapter extends RecyclerView.Adapter<PhoneticsViewHolder> {

    private   Context context;
    private List<Phonetics> phonetics;

    public PhoneticsAdapter(Context context, List<Phonetics> phonetics) {
        this.context = context;
        this.phonetics = phonetics;
    }

    @NonNull
    @Override
    public PhoneticsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PhoneticsViewHolder(LayoutInflater.from(context).inflate(R.layout.phonetics_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneticsViewHolder holder, int position) {

        holder.textview_phonetics.setText(phonetics.get(position).getText());
        holder.imageButton_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer player = new MediaPlayer();
                try {
                    player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    player.setDataSource("https:"+phonetics.get(position).getAudio());
                    player.prepare();
                    player.start();
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(context, "Error No Audio available", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return phonetics.size();
    }
}
