package com.example.ueben1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ueben1.adapter.LobbyAdapter;
import com.example.ueben1.lobby.Lobby;
import com.example.ueben1.retrofit.GameAPI;
import com.example.ueben1.retrofit.RetrofitService;
import com.google.android.material.slider.Slider;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GamesListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_list);

        //Post
        initializeComponents();

        //Get
        //recyclerView = findViewById(R.id.gamesList);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //loadGames();
    }
    private void loadGames(){
        RetrofitService retrofitService = new RetrofitService();
        GameAPI api = retrofitService.getRetrofit().create(GameAPI.class);
        api.getAllLobbys().enqueue(new Callback<List<Lobby>>() {
            @Override
            public void onResponse(Call<List<Lobby>> call, Response<List<Lobby>> response) {
                Logger.getLogger(GamesListActivity.class.getName()).log(Level.SEVERE, "A01010: Load worked:"+response);
                populateListView(response.body());
            }

            @Override
            public void onFailure(Call<List<Lobby>> call, Throwable throwable) {
                Logger.getLogger(GamesListActivity.class.getName()).log(Level.SEVERE, "A01010: Load failed:"+throwable);
                Toast.makeText(GamesListActivity.this, "Getting Lobbys failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void populateListView(List<Lobby> body) {
        LobbyAdapter lobbyAdapter = new LobbyAdapter(body);
        Logger.getLogger(GamesListActivity.class.getName()).log(Level.SEVERE, "A01010: createt adapter "+body);
        recyclerView.setAdapter(lobbyAdapter);
    }

    ;
    private void initializeComponents() {
        //Die Elemente der Seite beckommen
        Slider speedView = findViewById(R.id.createLobbySpeed);
        Slider maxPlayersView = findViewById(R.id.createLobbyMaxPlayers);
        Switch isPublicView = findViewById(R.id.createLobbyIsPublic);
        Button create = findViewById(R.id.createLobbyCreate);

        RetrofitService retrofitService = new RetrofitService();
        GameAPI api = retrofitService.getRetrofit().create(GameAPI.class);

        //Onclick auf den Button
        create.setOnClickListener(view -> {
            //Werte der Elemente Speichern
            int speed = (int) speedView.getValue();
            int maxPlayers = (int) maxPlayersView.getValue();
            boolean isPublic = isPublicView.isChecked();

            //Lobby Objekt erstellen und befüllen um das danach zu verschicken.
            //Hätte man evt. auch mit Konstruktor lösen können.
            Lobby lobby = new Lobby();
            lobby.setSpeed(speed);
            lobby.setIspublic(isPublic);
            lobby.setMaxplayer(maxPlayers);
            Logger.getLogger(GamesListActivity.class.getName()).log(Level.SEVERE, "Checkpoint: A1001; Speed: "+speed+" Public? "+isPublic+" max Players: "+maxPlayers);

            //Enque ist asynchron --> Die App wird nicht blockiert!
            api.createLobby(lobby).enqueue(new Callback<Lobby>() {
                @Override
                public void onResponse(Call<Lobby> call, Response<Lobby> response) {
                    if (response.isSuccessful()) {
                        Logger.getLogger(GamesListActivity.class.getName()).log(Level.INFO, "Checkpoint: A1002: Success");
                        Lobby lobbyResponse = response.body();

                        if (lobbyResponse != null) {
                            int id = lobbyResponse.getId();
                            // Now you can use the id
                            System.out.println("Lobby ID: " + id);
                            Logger.getLogger(GamesListActivity.class.getName()).log(Level.SEVERE, "Checkpoint: A1002: Response and successful"+id);
                            Toast.makeText(GamesListActivity.this, "Game created successfully", Toast.LENGTH_LONG).show();
                            // Weiterleitung zur Lobby
                        } else {
                            Logger.getLogger(GamesListActivity.class.getName()).log(Level.SEVERE, "Checkpoint: A1002: Successful but no response");
                            Toast.makeText(GamesListActivity.this, "Game created not successfully", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Logger.getLogger(GamesListActivity.class.getName()).log(Level.SEVERE, "Checkpoint: A1002: Response but not successful");
                        Toast.makeText(GamesListActivity.this, "Game created not successfully", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Lobby> call, Throwable throwable) {
                    Logger.getLogger(GamesListActivity.class.getName()).log(Level.SEVERE, "Checkpoint: A1002: Failed", throwable);
                    Toast.makeText(GamesListActivity.this, "Creating Game failed", Toast.LENGTH_LONG).show();
                }
            });

        });
    }
    public void goToMenu(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}