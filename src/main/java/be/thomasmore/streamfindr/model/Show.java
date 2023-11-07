package be.thomasmore.streamfindr.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity
public class Show {
    @Id
    private Integer id;
    private String name;
    private String director;
    private String genre;
    @Column(length = 500)
    private String plotDescription;
    private int firstYearAired;
    private int lastYearAired;
    private int numberOfSeasons;
    private String posterSrc;

    public Show() {
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

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPlotDescription() {
        return plotDescription;
    }

    public void setPlotDescription(String plotDescription) {
        this.plotDescription = plotDescription;
    }

    public int getFirstYearAired() {
        return firstYearAired;
    }

    public void setFirstYearAired(int firstYearAired) {
        this.firstYearAired = firstYearAired;
    }

    public int getLastYearAired() {
        return lastYearAired;
    }

    public void setLastYearAired(int lastYearAired) {
        this.lastYearAired = lastYearAired;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public String getPosterSrc() {
        return posterSrc;
    }

    public void setPosterSrc(String posterSrc) {
        this.posterSrc = posterSrc;
    }
}
