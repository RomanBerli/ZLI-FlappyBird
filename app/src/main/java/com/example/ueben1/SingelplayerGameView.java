//Author: Roman Berli
//Date: 26.6.24
//Version: 1.0
//Desc. Einzeilspieler game logik
package com.example.ueben1;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

import android.os.Handler;

import java.util.Random;

public class SingelplayerGameView extends View {
    Handler handler;
    Runnable runnable;
    int UPDATE_MILLIS = 33; //ca 30fps
    Bitmap background;
    Bitmap bird;
    Bitmap toptube, bottomtube;
    int birdheigt, birdwidth;
    Display display;
    Point point;
    int dWith, dHeight; //Device Height
    Rect rect;

    //Für Multiplayer diese Werte in eine Klasse tun
    int posX = 100, posY = 400, gravity = 3, velocity = -12;
    boolean gameState = true;
    int gap = 400; //grösse des Lochs
    int minTubeOffset, maxTubeOffset;
    int numberOfTubes = 4;
    int distanceBetweenTubes;
    int[] tubeX = new int[numberOfTubes];
    int[] topTubeY = new int[numberOfTubes];
    Random random;
    int tubeVelocity = 10;
    int score= 0;
    int backgroundX = 0;
    int schub = -25;

    public SingelplayerGameView(Context context) {
        super(context);
        System.out.println("trigger");
        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate(); //Calls on Draw
            }
        };
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.flappybird);
        int originalWidth = originalBitmap.getWidth();
        int originalHeight = originalBitmap.getHeight();
        float scale = 70.0f / originalHeight;
        birdwidth = Math.round(originalWidth * scale);
        birdheigt = Math.round(originalHeight * scale);
        bird = Bitmap.createScaledBitmap(originalBitmap, birdwidth, birdheigt, true);
        background = BitmapFactory.decodeResource(getResources(), R.drawable.flappybirdbg);

        //Originale Bilder
        toptube = BitmapFactory.decodeResource(getResources(), R.drawable.toppipe);
        bottomtube = BitmapFactory.decodeResource(getResources(), R.drawable.bottompipe);

        // Berechne die neue Breite als 30% der Bildschirmbreite
        int dWidth = getResources().getDisplayMetrics().widthPixels;
        int newWidth = (int) (dWidth * 0.2);

        // Berechne das Skalierungsverhältnis basierend auf der neuen Breite
        float scale2 = newWidth / (float) toptube.getWidth();
        int newHeightTop = (int) (toptube.getHeight() * scale2);
        int newHeightBottom = (int) (bottomtube.getHeight() * scale2);

        // Skaliere die Bitmaps auf die neuen Dimensionen
        Bitmap scaledTopTube = Bitmap.createScaledBitmap(toptube, newWidth, newHeightTop, true);
        Bitmap scaledBottomTube = Bitmap.createScaledBitmap(bottomtube, newWidth, newHeightBottom, true);

        // Weise die skalierten Bitmaps den Variablen zu
        toptube = scaledTopTube;
        bottomtube = scaledBottomTube;

        display = ((Activity) getContext()).getWindow().getWindowManager().getDefaultDisplay();
        point = new Point();
        display.getSize(point);
        dWith = point.x;
        dHeight = point.y;
        rect = new Rect(0, 0, dWith, dHeight);
        distanceBetweenTubes = dWith * 3 / 4;
        minTubeOffset = gap / 2;
        maxTubeOffset = dHeight - minTubeOffset - gap;
        random = new Random();

        for (int i = 0; i < numberOfTubes; i++) {
            tubeX[i] = dWith + i * distanceBetweenTubes;
            topTubeY[i] = minTubeOffset + random.nextInt(maxTubeOffset - minTubeOffset + 1);
        }
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        //Hier wird gezeichnet.

        backgroundX -= 5;
        if (backgroundX <= -background.getWidth()) {
            backgroundX = 0;
        }
        // Zeichne das gestreckte Hintergrundbild
        Rect destRect1 = new Rect(backgroundX, 0, backgroundX + dWith, dHeight);
        Rect destRect2 = new Rect(backgroundX + dWith, 0, backgroundX + 2 * dWith, dHeight);

        // Zeichne das Hintergrundbild
        canvas.drawBitmap(background, null, destRect1, null);
        canvas.drawBitmap(background, null, destRect2, null);


        if (gameState) {
            if (posY > dHeight) {
                gameState = false;
            }
            if (posY > 10 && posY < dHeight / 15 * 14 + 1) {
                velocity += gravity;
                posY += velocity;
            } else if (posY < 30) {
                velocity = gravity;
                posY += velocity;
            }

            for (int i = 0; i < numberOfTubes; i++) {
                tubeX[i] -= tubeVelocity;
                if (tubeX[i] < -toptube.getWidth()){
                    tubeX[i] += numberOfTubes * distanceBetweenTubes;
                    topTubeY[i] = minTubeOffset + random.nextInt(maxTubeOffset - minTubeOffset + 1);
                    score++;

                    if (score % 3 == 0){
                        tubeVelocity++;
                    }
                    if (score % 4 == 0){
                        gravity++;
                        schub--;
                    }
                }
                canvas.drawBitmap(toptube, tubeX[i], topTubeY[i] - toptube.getHeight(), null);
                canvas.drawBitmap(bottomtube, tubeX[i], topTubeY[i] + gap, null);

                if (posX + birdwidth > tubeX[i] && posX < tubeX[i] + toptube.getWidth()) {
                    if (posY < topTubeY[i] || posY + birdheigt > topTubeY[i] + gap) {
                        gameState = false;
                    }
                }


            }
        }



        // Hintergrundfarbe für den Text
        Paint backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.parseColor("#ffec99")); // Hellgelbe Hintergrundfarbe
        backgroundPaint.setAntiAlias(true);

        Paint borderPaint = new Paint();
        borderPaint.setColor(Color.parseColor("#846358")); // Braune Hintergrundfarbe
        borderPaint.setAntiAlias(true);

        // Textfarbe und Eigenschaften
        Paint textPaint = new Paint();
        textPaint.setColor(Color.BLACK); // Textfarbe
        textPaint.setTextSize(50); // Textgröße
        textPaint.setAntiAlias(true); // Anti-Aliasing für glatte Kanten

        String text = "Score: " + score;

        // Textposition
        float x = 100;
        float y = 100;

        // Textbreite und -höhe berechnen
        Rect textBounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), textBounds);
        float textWidth = textBounds.width();
        float textHeight = textBounds.height();

        // Radius für die abgerundeten Ecken
        float cornerRadius = 60;

        // Rechteck mit abgerundeten Ecken zeichnen (Hintergrund für den Text)
        float padding = 20;
        float boarderWidth = 15;

        float rectLeft = x - 10-padding;
        float rectTop = y - textHeight-padding;
        float rectRight = x + textWidth + 10+padding;
        float rectBottom = y + 10+padding;

        canvas.drawRoundRect(rectLeft-boarderWidth, rectTop-boarderWidth, rectRight+boarderWidth, rectBottom+boarderWidth, cornerRadius, cornerRadius, borderPaint);
        canvas.drawRoundRect(rectLeft, rectTop, rectRight, rectBottom, cornerRadius, cornerRadius, backgroundPaint);


        canvas.drawBitmap(bird, posX, posY, null);
        if (gameState){
            // Text zeichnen
            canvas.drawText(text, x, y, textPaint);
        } else {
            Intent intent = new Intent(getContext(), SingelplayerGameOverActivity.class);
            intent.putExtra("SCORE", score);
            getContext().startActivity(intent);
        }

        if (gameState) {
            handler.postDelayed(runnable, UPDATE_MILLIS);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("Click");
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            if(velocity > -10) {
                velocity = schub;
                gravity = 3;
            } else if (velocity > -40){
                velocity += schub;
                gravity = 7;
            }
        }
        return true;
    }
}
