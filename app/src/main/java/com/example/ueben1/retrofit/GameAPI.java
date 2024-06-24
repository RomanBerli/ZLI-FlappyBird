package com.example.ueben1.retrofit;

import com.example.ueben1.lobby.Lobby;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface GameAPI {
    @GET("/lobby")
    Call<List<Lobby>> getAllLobbys();

    @POST("/lobby")
    Call<Lobby> createLobby(@Body Lobby lobby);
}
