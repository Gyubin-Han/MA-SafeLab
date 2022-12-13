package com.example.myapplication909;

public class Kor_cas {
    private String rank;
    private String country;
    private String population;

    public Kor_cas(String rank, String country, String population) {
        this.rank = rank;
        this.country = country;
        this.population = population;
    }

    public String getRank() {
        return this.rank;
    }

    public String getCountry() {
        return this.country;
    }

    public String getPopulation() {
        return this.population;
    }
}
