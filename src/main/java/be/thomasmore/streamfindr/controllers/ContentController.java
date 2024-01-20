package be.thomasmore.streamfindr.controllers;
import be.thomasmore.streamfindr.controllers.admin.ContentAdminController;
import be.thomasmore.streamfindr.model.*;
import be.thomasmore.streamfindr.repositories.AccountRepository;
import be.thomasmore.streamfindr.repositories.ContentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ContentController {
    private final Logger logger = LoggerFactory.getLogger(ContentAdminController.class);

    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private AccountRepository accountRepository;

    @ModelAttribute("content")
    public Content findContent(@PathVariable(required = false) Integer id) {
        if (id != null) {
            Optional<Content> optionalContent = contentRepository.findById(id);
            if (optionalContent.isPresent()) return optionalContent.get();
        }
        return null;
    }

    @GetMapping({"/", "/home"})
    public String home(Model model) {

        List<Content> allContent = contentRepository.findAll();
        allContent.sort(Comparator.comparing(Content::getName));
        List<Content> mostRecentContent = findMostRecent(allContent);

        List<Content> top3Reviewed = contentRepository.findTop3ReviewedContent();

        model.addAttribute("content", mostRecentContent);
        model.addAttribute("top3", top3Reviewed);
        return "home";
    }

    private List<Content> findMostRecent(List<Content> sortedContent) {
        //Setting the year fixed as the data will not be updated later on
        int currentYear = 2023;
        List<Content> mostRecentContent = sortedContent.stream()
                .filter(c -> (c instanceof Movie && ((Movie) c).getYearReleased() == currentYear + 1) ||
                        (c instanceof Show && ((Show) c).getLastYearAired() == currentYear + 1))
                .limit(6)
                .collect(Collectors.toList());

        if (mostRecentContent.size() < 6) {
            mostRecentContent.addAll(sortedContent.stream()
                    .filter(c -> (c instanceof Movie && ((Movie) c).getYearReleased() == currentYear) ||
                            (c instanceof Show && ((Show) c).getLastYearAired() == currentYear))
                    .limit(6 - mostRecentContent.size())
                    .collect(Collectors.toList()));
        }

        return mostRecentContent;
    }

    @GetMapping({"/contentlist", "/contentlist/"})
    public String contentList(Model model) {
        List<Content> allContent = contentRepository.findAll();
        model.addAttribute("content", allContent);
        return "contentlist";
    }

    @GetMapping("/contentlist/filter")
    public String contentListWithFilter(Model model,
                                        @RequestParam(required = false) String contentType,
                                        @RequestParam(required = false) String genre,
                                        @RequestParam(required = false) String platform,
                                        @RequestParam(required = false) String keyword,
                                        @RequestParam(required = false) Integer minScore) {

        List<Content> filteredContent = contentRepository.findByCombinedFilter(contentType,
                genre, platform, keyword, minScore);

        model.addAttribute("showFilters", true);
        model.addAttribute("content", filteredContent);
        model.addAttribute("contentType", contentType);
        model.addAttribute("selectedGenre", genre);
        model.addAttribute("selectedPlatform", platform);
        model.addAttribute("keyword", keyword);
        model.addAttribute("minScore", minScore);
        model.addAttribute("distinctGenres", contentRepository.findDistinctGenres());
        model.addAttribute("distinctPlatformNames", contentRepository.findDistinctPlatformNames());
        return "contentlist";
    }

    @GetMapping({"/contentdetails", "/contentdetails/", "/contentdetails/{id}"})
    public String contentDetails(Model model,
                                 @ModelAttribute("content") Content content,
                                 @PathVariable(required = false) Integer id,
                                 Principal principal) {

        //Doorgeven welke user ingelogd is via Principal, en of content in favorieten zit.

        if (content != null) {
            Content foundContentForAccount = null;
            if (principal != null) {
                Optional<Account> optionalAccount = accountRepository.findByUsername(principal.getName());
                if (optionalAccount.isPresent()) {
                    Account account = optionalAccount.get();
                    foundContentForAccount = findContentById(account.getContent(), id);
                }
            }

            Optional<Content> prevContent = contentRepository.findFirstByIdLessThanOrderByIdDesc(id);
            if (prevContent.isEmpty()) prevContent = contentRepository.findFirstByOrderByIdDesc();

            Optional<Content> nextContent = contentRepository.findFirstByIdGreaterThanOrderByIdAsc(id);
            if (nextContent.isEmpty()) nextContent = contentRepository.findFirstByOrderByIdAsc();

            model.addAttribute("prevContent", prevContent.get().getId());
            model.addAttribute("nextContent", nextContent.get().getId());
            model.addAttribute("averageRating", contentRepository.calculateAverageRatingForContent(content));
            model.addAttribute("inFavourites", foundContentForAccount != null);
        }

        return "contentdetails";
    }

    @PostMapping({"/contentfavourite", "/contentfavourite/{id}"})
    public String contentFavourite(Model model,
                                   Principal principal,
                                   @PathVariable(required = false) Integer id) {
        if (id == null) return "redirect:/contentlist";
        if (principal == null) return "redirect:/contentdetails/" + id;

        Optional<Content> optionalContent = contentRepository.findById(id);
        Optional<Account> optionalAccount = accountRepository.findByUsername(principal.getName());

        if (optionalContent.isPresent() && optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            //check if the content is already in account favourites
            Content content = findContentById(account.getContent(), id);
            if (content == null) {
                account.getContent().add(optionalContent.get());
            } else {
                account.getContent().remove(content);
            }
            accountRepository.save(account);
        }
        return "redirect:/contentdetails/" + id;
    }

    private Content findContentById(Collection<Content> content, int contentId) {
        for (Content contentItem : content) {
            if (contentItem.getId() == contentId) return contentItem;
        }
        return null;
    }

}
