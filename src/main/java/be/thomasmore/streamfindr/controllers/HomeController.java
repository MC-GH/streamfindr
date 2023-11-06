package be.thomasmore.streamfindr.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/home"})
    public String home() {
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
