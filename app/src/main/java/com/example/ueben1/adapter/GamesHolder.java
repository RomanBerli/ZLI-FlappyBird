package com.example.ueben1.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ueben1.R;

public class GamesHolder extends RecyclerView.ViewHolder {
    TextView id, speed, players;
    Button join;
    public GamesHolder(@NonNull View itemView) {
        super(itemView);
        id = itemView.findViewById(R.id.lobbyList_id);
        players = itemView.findViewById(R.id.lobbyList_players);
        speed = itemView.findViewById(R.id.lobbyList_speed);
        join = itemView.findViewById(R.id.lobbyList_join);

    }
}
