package be.thomasmore.streamfindr.controllers;

import be.thomasmore.streamfindr.model.Platform;
import be.thomasmore.streamfindr.repositories.PlatformRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PlatformController {

    Logger logger = LoggerFactory.getLogger(ContentController.class);


    @Autowired
    private PlatformRepository platformRepository;

    @GetMapping({"/platformlist", "/platformlist/"})
    public String platformList(Model model) {
        List<Platform> allPlatforms = platformRepository.findAll();
        model.addAttribute("platforms", allPlatforms);
        return "platformlist";
    }

    @GetMapping("/platformlist/filter")
    public String platformListWithFilter(Model model,
                                         @RequestParam(required=false) String keyword,
                                         @RequestParam(required=false) Integer maxMonthlyFee,
                                         @RequestParam(required = false) Boolean annualSubscriptionPossible) {

        logger.info(String.format("platformlist with filter -- keyword=%s, annualsubscriptionpossible=%b",
                        keyword, annualSubscriptionPossible));

        List<Platform> allPlatforms = platformRepository.findByCombinedFilter(keyword, annualSubscriptionPossible);
        model.addAttribute("platforms", allPlatforms);
        model.addAttribute("keyword",keyword);
        model.addAttribute("maxMonthlyFee", maxMonthlyFee);
        model.addAttribute("annualSubscriptionPossible", annualSubscriptionPossible);
        model.addAttribute("lowestFee", platformRepository.findFirstByOrderByMonthlyPriceAsc().get().getMonthlyPrice());
        model.addAttribute("highestFee", platformRepository.findFirstByOrderByMonthlyPriceDesc().get().getMonthlyPrice());
        model.addAttribute("showFilters", true);
        return "platformlist";
    }
}
