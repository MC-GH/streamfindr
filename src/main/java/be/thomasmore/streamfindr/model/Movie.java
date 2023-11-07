package be.thomasmore.streamfindr.model;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Movie")
public class Movie extends Content {
    private int yearReleased;

    public Movie() {}

    public int getYearReleased() {
        return yearReleased;
    }

    public void setYearReleased(int yearReleased) {
        this.yearReleased = yearReleased;
    }
}

