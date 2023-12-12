package be.thomasmore.streamfindr.controllers;

import be.thomasmore.streamfindr.model.Content;
import be.thomasmore.streamfindr.model.Platform;
import be.thomasmore.streamfindr.repositories.PlatformRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class PlatformController {
    Logger logger = LoggerFactory.getLogger(ContentController.class);
    @Autowired
    private PlatformRepository platformRepository;

    @ModelAttribute("platform")
    public Platform findPlatform(@PathVariable (required = false) Integer id) {
        logger.info("findPlatform" + id);
        if(id!=null) {
            Optional<Platform> optionalPlatform = platformRepository.findById(id);
            if(optionalPlatform.isPresent()) return optionalPlatform.get();
        }
        return null;
    }
    @GetMapping({"/platformlist", "/platformlist/"})
    public String platformList(Model model) {
        List<Platform> allPlatforms = platformRepository.findAll();
        model.addAttribute("platforms", allPlatforms);
        return "platformlist";
    }

    @GetMapping("/platformlist/filter")
    public String platformListWithFilter(Model model,
                                         @RequestParam(required = false) String keyword,
                                         @RequestParam(required = false) Double maxMonthlyFee,
                                         @RequestParam(required = false) Boolean annualSubscriptionPossible) {

        logger.info(String.format("platformlist with filter -- keyword=%s, maxFee=%f annualsubscriptionpossible=%b",
                keyword, maxMonthlyFee, annualSubscriptionPossible));

        List<Platform> platforms = platformRepository.findByCombinedFilter(keyword, maxMonthlyFee, annualSubscriptionPossible);
        model.addAttribute("platforms", platforms);
        model.addAttribute("keyword", keyword);
        model.addAttribute("maxMonthlyFee", maxMonthlyFee);
        model.addAttribute("annualSubscriptionPossible", annualSubscriptionPossible);
        model.addAttribute("lowestFee", platformRepository.findFirstByOrderByMonthlyPriceInUsdAsc().get().getMonthlyPriceInUsd());
        model.addAttribute("highestFee", platformRepository.findFirstByOrderByMonthlyPriceInUsdDesc().get().getMonthlyPriceInUsd());
        model.addAttribute("showFilters", true);
        return "platformlist";
    }

    @GetMapping({"/platformdetails","/platformdetails/","/platformdetails/{id}"})
    public String platformDetails(Model model,
                                  @ModelAttribute("platform") Platform platform,
                                  @PathVariable (required = false) Integer id) {
        if(platform != null) {
            Optional<Platform> prevPlatform = platformRepository.findFirstByIdLessThanOrderByIdDesc(id);
            if(prevPlatform.isEmpty()) prevPlatform = platformRepository.findFirstByOrderByIdDesc();

            Optional<Platform> nextPlatform = platformRepository.findFirstByIdGreaterThanOrderByIdAsc(id);
            if(nextPlatform.isEmpty()) nextPlatform = platformRepository.findFirstByOrderByIdAsc();

            model.addAttribute("prevPlatform", prevPlatform.get().getId());
            model.addAttribute("nextPlatform", nextPlatform.get().getId());
//            model.addAttribute("averageRating", contentRepository.calculateAverageRatingForContent(content));
        }

        return "platformdetails";
    }
}
