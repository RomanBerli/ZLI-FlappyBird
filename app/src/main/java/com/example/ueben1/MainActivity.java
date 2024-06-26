//Author: Roman Berli
//Date: 26.6.24
//Version: 1.0
//Desc. Main Activity, das erste was angezeigt wird.

package com.example.ueben1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void startSingelplayerGame(View view){
        Intent intent = new Intent(this, SingelplayerGame.class);
        startActivity(intent);
        finish();
    }
    public void startMultiplayerGame(View view){
        Intent intent = new Intent(this, GamesListActivity.class);
        startActivity(intent);
        finish();
    }
}