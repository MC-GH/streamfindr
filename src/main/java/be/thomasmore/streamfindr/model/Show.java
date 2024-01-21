package be.thomasmore.streamfindr.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Show")
public class Show extends Content {
    private Integer firstYearAired;
    private Integer lastYearAired;
    private Integer numberOfSeasons;

    public Show() {
    }

    public Integer getFirstYearAired() {
        return firstYearAired;
    }

    public void setFirstYearAired(Integer firstYearAired) {
        this.firstYearAired = firstYearAired;
    }

    public Integer getLastYearAired() {
        return lastYearAired;
    }

    public void setLastYearAired(Integer lastYearAired) {
        this.lastYearAired = lastYearAired;
    }

    public Integer getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(Integer numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }
}
