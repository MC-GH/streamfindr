package be.thomasmore.streamfindr.controllers;

import be.thomasmore.streamfindr.model.Platform;
import be.thomasmore.streamfindr.repositories.PlatformRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PlatformController {

    @Autowired
    private PlatformRepository platformRepository;

    @GetMapping({"/platformlist", "/platformlist/"})
    public String platformList(Model model) {
        List<Platform> allPlatforms = platformRepository.findAll();
        model.addAttribute("platforms", allPlatforms);
        return "platformlist";
    }

}
