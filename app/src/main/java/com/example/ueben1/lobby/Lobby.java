//Author: Roman Berli
//Date: 26.6.24
//Version: 1.0
//Desc. Lobby Klasse welche im Forum gefüllt und verschickt wird.

package com.example.ueben1.lobby;

public class Lobby {
    private int maxplayer;
    private int speed;
    private boolean ispublic;
    private String owner;
    private int id;

    public int getMaxplayer() {
        return maxplayer;
    }

    public void setMaxplayer(int maxplayer) {
        this.maxplayer = maxplayer;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isIspublic() {
        return ispublic;
    }

    public void setIspublic(boolean ispublic) {
        this.ispublic = ispublic;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getId() {
        return id;
    }
    public void setId(int id){this.id = id;}
}
