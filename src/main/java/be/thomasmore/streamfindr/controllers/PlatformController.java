package be.thomasmore.streamfindr.controllers;

import be.thomasmore.streamfindr.model.Account;
import be.thomasmore.streamfindr.model.Content;
import be.thomasmore.streamfindr.model.Platform;
import be.thomasmore.streamfindr.repositories.AccountRepository;
import be.thomasmore.streamfindr.repositories.PlatformRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
public class PlatformController {
    @Autowired
    private PlatformRepository platformRepository;
    @Autowired
    private AccountRepository accountRepository;

    @ModelAttribute("platform")
    public Platform findPlatform(@PathVariable (required = false) Integer id) {
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
                                  @PathVariable (required = false) Integer id,
                                  Principal principal) {
        if(platform != null) {
            Platform foundPlatformForAccount = null;
            if (principal != null) {
                Optional<Account> optionalAccount = accountRepository.findByUsername(principal.getName());
                if(optionalAccount.isPresent()) {
                    Account account = optionalAccount.get();
                    foundPlatformForAccount = findPlatformById(account.getPlatforms(),id);
                }
            }

            Optional<Platform> prevPlatform = platformRepository.findFirstByIdLessThanOrderByIdDesc(id);
            if(prevPlatform.isEmpty()) prevPlatform = platformRepository.findFirstByOrderByIdDesc();

            Optional<Platform> nextPlatform = platformRepository.findFirstByIdGreaterThanOrderByIdAsc(id);
            if(nextPlatform.isEmpty()) nextPlatform = platformRepository.findFirstByOrderByIdAsc();

            model.addAttribute("prevPlatform", prevPlatform.get().getId());
            model.addAttribute("nextPlatform", nextPlatform.get().getId());
            model.addAttribute("inFavourites",foundPlatformForAccount != null);
        }

        return "platformdetails";
    }

    @PostMapping({"/platformfavourite","/platformfavourite/{id}"})
    public String platformFavourite(Model model,
                                   Principal principal,
                                   @PathVariable(required = false) Integer id) {
        if(id==null) return "redirect:/platformlist";
        if(principal==null) return "redirect:/platforrmdetails/" + id;

        Optional<Platform> optionalPlatform = platformRepository.findById(id);
        Optional<Account> optionalAccount = accountRepository.findByUsername(principal.getName());

        if(optionalPlatform.isPresent() && optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            //check if the platform is already in account favourites
            Platform platform = findPlatformById(account.getPlatforms(),id);
            if(platform == null) {
                account.getPlatforms().add(optionalPlatform.get());
            } else {
                account.getPlatforms().remove(platform);
            }
            accountRepository.save(account);
        }
        return "redirect:/platformdetails/" + id;
    }

    private Platform findPlatformById(Collection<Platform> platforms, int platformId) {
        for(Platform platform : platforms) {
            if(platform.getId() == platformId) return platform;
        }
        return null;
    }
}
