package be.thomasmore.streamfindr.controllers;

import be.thomasmore.streamfindr.repositories.PlatformRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PlatformController {

    @Autowired
    private PlatformRepository platformRepository;

    @GetMapping("/platformlist")
    public String platformList() {
        return "platformlist";
    }

}
