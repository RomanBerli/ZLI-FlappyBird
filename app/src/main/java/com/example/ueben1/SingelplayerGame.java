//Author: Roman Berli
//Date: 26.6.24
//Version: 1.0
//Desc. Kleine klasse welche nach dem erstellen die gameview klasse aufruft.

package com.example.ueben1;

import android.app.Activity;
import android.app.GameState;
import android.os.Bundle;

import androidx.annotation.Nullable;

public class SingelplayerGame extends Activity {

    SingelplayerGameView gameView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameView = new SingelplayerGameView(this);
        setContentView(gameView);
    }
}
