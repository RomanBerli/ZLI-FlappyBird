package com.example.ueben1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ueben1.R;
import com.example.ueben1.lobby.Lobby;

import java.util.List;

public class LobbyAdapter extends RecyclerView.Adapter<GamesHolder>{

    private List<Lobby> lobbyList;
    public LobbyAdapter(List <Lobby> lobbyList){
        this.lobbyList = lobbyList;
    }

    @NonNull
    @Override
    public GamesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_lobby_item, parent, false);
        return new GamesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GamesHolder holder, int position) {
        Lobby lobby = lobbyList.get(position);
        holder.id.setText(lobby.getId());
        holder.speed.setText(lobby.getSpeed());
        holder.players.setText(lobby.getMaxplayer());
    }

    @Override
    public int getItemCount() {
        return lobbyList.size();
    }
}
