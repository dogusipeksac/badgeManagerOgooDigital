package com.example.badgemanageogoodigital.Model;

public class BadgeData {

    private int badgeId;
    private int id;
    private String badgeTitle;
    private int howmany;
    private float avarage;

    public BadgeData(int id, String title) {
        this.badgeId = id;
        this.badgeTitle = title;
    }

    public BadgeData(int badgeId, int id, String badgeTitle, int howmany, float avarage) {
        this.badgeId = badgeId;
        this.id = id;
        this.badgeTitle = badgeTitle;
        this.howmany = howmany;
        this.avarage = avarage;
    }

    public BadgeData() {
    }

    public int getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(int badgeId) {
        this.badgeId = badgeId;
    }

    public String getBadgeTitle() {
        return badgeTitle;
    }

    public void setBadgeTitle(String badgeTitle) {
        this.badgeTitle = badgeTitle;
    }

    public int getHowmany() {
        return howmany;
    }

    public void setHowmany(int howmany) {
        this.howmany = howmany;
    }

    public float getAvarage() {
        return avarage;
    }

    public void setAvarage(float avarage) {
        this.avarage = avarage;
    }

    public int getId() {
        return badgeId;
    }

    public void setId(int id) {
        this.badgeId = id;
    }

    public String getTitle() {
        return badgeTitle;
    }

    public void setTitle(String title) {
        this.badgeTitle = title;
    }
}
