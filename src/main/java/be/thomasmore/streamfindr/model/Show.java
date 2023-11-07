package be.thomasmore.streamfindr.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity
@DiscriminatorValue("Show")
public class Show extends Content{
        private int firstYearAired;
        private int lastYearAired;
        private int numberOfSeasons;
        public Show() {
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
}
