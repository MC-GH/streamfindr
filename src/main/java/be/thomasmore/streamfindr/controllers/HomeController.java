package be.thomasmore.streamfindr.controllers;
import be.thomasmore.streamfindr.model.Show;
import be.thomasmore.streamfindr.repositories.MovieRepository;
import be.thomasmore.streamfindr.repositories.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class HomeController {

    @Autowired
    private ShowRepository showRepository;

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        //Get date of today, fetch latest 9 movies that were released to display
        //Store in Iterable
        Iterable<Show> allShows = showRepository.findAll();
        model.addAttribute("shows", allShows);
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
