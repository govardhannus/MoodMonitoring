package com.example.govardhan.wearpracticeconnection;

/**
 * Created by Govardhan on 13/5/2017.
 */

public class Angry {

    private int id;
    private String Angry;
    private String date;
    private String Time;

    public Angry(int id, String angry, String date) {
        this.id = id;
        Angry = angry;
        this.date = date;
    }

//    public Angry(int id, String angryperson, String date) {
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAngry() {
        return Angry;
    }

    public void setAngry(String angry) {
        Angry = angry;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        date = date;
    }




}
