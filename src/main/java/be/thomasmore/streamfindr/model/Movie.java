package be.thomasmore.streamfindr.model;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Movie")
public class Movie extends Content {
    private Integer yearReleased;
    public Movie() {}

    public Integer getYearReleased() {
        return yearReleased;
    }

    public void setYearReleased(Integer yearReleased) {
        this.yearReleased = yearReleased;
    }
}

