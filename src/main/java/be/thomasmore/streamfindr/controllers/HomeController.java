package be.thomasmore.streamfindr.controllers;

import be.thomasmore.streamfindr.model.Movie;
import be.thomasmore.streamfindr.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class HomeController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        //Get date of today, fetch latest 9 movies that were released to display
        //Store in Iterable
        Iterable<Movie> allMovies = movieRepository.findAll();
        model.addAttribute("movies", allMovies);
        return "home";
    }

    @GetMapping("/contentlist")
    public String contentList() {
        return "contentlist";
    }

    @GetMapping("/platformlist")
    public String platformList() {
        return "platformlist";
    }

    @GetMapping("/contentdetails")
    public String contentDetails() {
        return "contentdetails";
    }


}
