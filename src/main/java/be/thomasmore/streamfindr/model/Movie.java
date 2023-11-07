package be.thomasmore.streamfindr.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.ArrayList;

@Entity
public class Movie {
    @Id
    private Integer id;
    private String name;
    private String genre;
    private int yearReleased;
    private String posterSrc;
//    private ArrayList<String> cast;

    public Movie() {}

    public Movie(Integer id, String name, String genre, int yearReleased, String posterSrc) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.yearReleased = yearReleased;
        this.posterSrc = posterSrc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getYearReleased() {
        return yearReleased;
    }

    public void setYearReleased(int yearReleased) {
        this.yearReleased = yearReleased;
    }

    public String getPosterSrc() {
        return posterSrc;
    }

    public void setPosterSrc(String posterSrc) {
        this.posterSrc = posterSrc;
    }
}

