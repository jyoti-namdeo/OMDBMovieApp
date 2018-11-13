package com.example.rtwm38.myomdbapp.model;

public class Movie  {
    public String Title;
    public String Year;
    public String imdbID;
    public String Type;
    public String Poster;

    public Movie (String poster, String title, String year) {
        this.Poster = poster;
        this.Title = title;
        this.Year = year;
    }


    @Override
    public String toString() {
        return "\nMovie{" +
                "Title='" + Title + '\'' +
                ", Year='" + Year + '\'' +
                ", imdbID='" + imdbID + '\'' +
                ", Type='" + Type + '\'' +
                ", Poster='" + Poster + '\'' +
                '}';
    }

}