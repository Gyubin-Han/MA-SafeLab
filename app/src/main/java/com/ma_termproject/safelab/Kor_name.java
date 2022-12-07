package com.example.myapplication909;


public class Kor_name {
    private String cas;
    private String eng;
    private String kor;

    public Kor_name(String cas, String eng, String kor) {
        this.cas = cas;
        this.eng = eng;
        this.kor = kor;
    }

    public String getRank() {
        return this.cas;
    }

    public String getCountry() {
        return this.eng;
    }

    public String getPopulation() {
        return this.kor;
    }
}