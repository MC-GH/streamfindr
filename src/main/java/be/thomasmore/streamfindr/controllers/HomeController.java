package be.thomasmore.streamfindr.controllers;

import be.thomasmore.streamfindr.model.Movie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class HomeController {

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        //Get date of today, fetch latest 9 movies that were released to display
        //Store in Iterable

        Movie movie = new Movie(1,"The Ring","Drama",2020,"/img/demon.jpg");
        model.addAttribute("movie",movie);

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
