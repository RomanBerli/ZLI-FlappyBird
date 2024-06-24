package com.example.ueben1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class SingelplayerGameOverActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singelplayer_gameover); // Layout für das Spielende

        // Punktestand aus dem Intent empfangen
        int score = getIntent().getIntExtra("SCORE", 0);

        // Highscore laden
        int highscore = 0;
        try {
            highscore = Integer.parseInt(readFromFile(this).trim());
        } catch (NumberFormatException e) {
            highscore = 0; // default to 0 if there's a problem parsing
        }

        // Highscore überschreiben und anzeigen
        TextView scoreTextView = findViewById(R.id.scoreTextView);
        TextView highscoreTextView = findViewById(R.id.textView);

        if (score > highscore) {
            writeToFile(Integer.toString(score), this);
            scoreTextView.setText("New Highscore: " + score);
            highscoreTextView.setText("Highscore: " + score);
        } else {
            scoreTextView.setText("Score: " + score);
            highscoreTextView.setText("Highscore: " + highscore);
        }
    }

    public void startSingelplayerGame(View view) {
        Intent intent = new Intent(this, SingelplayerGame.class);
        startActivity(intent);
        finish();
    }

    public void goToMenu(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private String readFromFile(Context context) {
        String ret = "";

        try {
            FileInputStream inputStream = context.openFileInput("highscore.txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString;
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Highscore could not be found! " + e, Toast.LENGTH_SHORT).show();
            writeToFile("0", context); // initialize highscore file with 0
            return "0";
        } catch (IOException e) {
            Toast.makeText(this, "Highscore could not be read! " + e, Toast.LENGTH_SHORT).show();
            return "0";
        }

        return ret;
    }

    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("highscore.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Toast.makeText(this, "Highscore could not be saved! " + e, Toast.LENGTH_SHORT).show();
        }
    }
}
