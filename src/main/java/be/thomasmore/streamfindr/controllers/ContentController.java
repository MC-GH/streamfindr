package be.thomasmore.streamfindr.controllers;

import be.thomasmore.streamfindr.model.*;
import be.thomasmore.streamfindr.repositories.ContentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class ContentController {

    Logger logger = LoggerFactory.getLogger(ContentController.class);

    @Autowired
    private ContentRepository contentRepository;

    @ModelAttribute("content")
    public Content findContent(@PathVariable (required = false) Integer id) {
        if(id!=null) {
            Optional<Content> optionalContent = contentRepository.findById(id);
            if(optionalContent.isPresent()) return optionalContent.get();
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
        model.addAttribute("top3",top3Reviewed);
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
        model.addAttribute("content",allContent);
        return "contentlist";
    }

    @GetMapping("/contentlist/filter")
    public String contentListWithFilter(Model model,
                                        @RequestParam(required = false) String contentType,
                                        @RequestParam(required = false) String genre,
                                        @RequestParam(required = false) String platform,
                                        @RequestParam(required = false) String keyword,
                                        @RequestParam(required = false) Integer minScore) {

        List<Content> filteredContent = contentRepository.findByCombinedFilter(convertStringToClassType(contentType),
                genre,platform,keyword,minScore);

        model.addAttribute("showFilters",true);
        model.addAttribute("content",filteredContent);
        model.addAttribute("contentType",contentType);
        model.addAttribute("selectedGenre",genre);
        model.addAttribute("selectedPlatform", platform);
        model.addAttribute("keyword",keyword);
        model.addAttribute("minScore",minScore);

        model.addAttribute("distinctGenres",contentRepository.findDistinctGenres());
        model.addAttribute("distinctPlatformNames", contentRepository.findDistinctPlatformNames());
        return "contentlist";
    }

    public Class<? extends Content> convertStringToClassType(String contentType) {
        if(contentType != null) {
            if(contentType.equals("Movie")) { return Movie.class;}
            if(contentType.equals("Show")) { return Show.class;}
        }
        return null;
    }

    @GetMapping({"/contentdetails","/contentdetails/","/contentdetails/{id}"})
    public String contentDetails(Model model,
                                 @ModelAttribute("content") Content content,
                                 @PathVariable(required = false) Integer id) {

        if(content != null) {
            Optional<Content> prevContent = contentRepository.findFirstByIdLessThanOrderByIdDesc(id);
            if(prevContent.isEmpty()) prevContent = contentRepository.findFirstByOrderByIdDesc();

            Optional<Content> nextContent = contentRepository.findFirstByIdGreaterThanOrderByIdAsc(id);
            if(nextContent.isEmpty()) nextContent = contentRepository.findFirstByOrderByIdAsc();

            model.addAttribute("prevContent", prevContent.get().getId());
            model.addAttribute("nextContent", nextContent.get().getId());
            model.addAttribute("averageRating", contentRepository.calculateAverageRatingForContent(content));
        }

        return "contentdetails";
    }

}
