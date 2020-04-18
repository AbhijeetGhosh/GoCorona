package com.example.gocorona;

public class countryScore {

    String country;
    int score;

    public countryScore(String country, int score) {
        this.country = country;
        this.score = score;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
