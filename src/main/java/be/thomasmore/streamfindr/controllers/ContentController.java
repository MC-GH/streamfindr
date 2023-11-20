package be.thomasmore.streamfindr.controllers;

import be.thomasmore.streamfindr.model.*;
import be.thomasmore.streamfindr.repositories.ContentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.StreamSupport;

@Controller
public class ContentController {

    Logger logger = LoggerFactory.getLogger(ContentController.class);

    @Autowired
    private ContentRepository contentRepository;

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        Iterable<Content> allContent = contentRepository.findAll();
        //StreamSupport used to create Stream, Spliterator used to create Stream from Iterator as Iterator itself does not have a stream() method.
        List<Content> list = StreamSupport.stream(allContent.spliterator(), false).sorted(Comparator.comparing(Content::getName)).toList();
        List<Content> mostRecentContent = findMostRecent(list);

        List<Content> top3Reviewed = contentRepository.findTop3ReviewedContent();

        model.addAttribute("content", mostRecentContent);
        model.addAttribute("top3",top3Reviewed);

        return "home";
    }

    private List<Content> findMostRecent(List<Content> sortedContent) {
        //Setting the year fixed as the data will not be updated later on
        int currentYear = 2023;
        List<Content> mostRecentContent = new ArrayList<>();
        for (Content c : sortedContent) {
            if (c != null) {
                if (c instanceof Movie) {
                    Movie movie = (Movie) c;
                    if (movie.getYearReleased() == currentYear + 1) {
                        mostRecentContent.add(c);
                    }
                } else if (c instanceof Show) {
                    Show show = (Show) c;
                    if (show.getLastYearAired() == currentYear + 1) {
                        mostRecentContent.add(c);
                    }
                }
                if (mostRecentContent.size() == 6) {
                    break;
                }
            }
        }

        if (mostRecentContent.size() <= 6) {
            for (Content c : sortedContent) {
                if (c != null) {
                    if (c instanceof Movie) {
                        Movie movie = (Movie) c;
                        if (movie.getYearReleased() == currentYear) {
                            mostRecentContent.add(c);
                        }
                    } else if (c instanceof Show) {
                        Show show = (Show) c;
                        if (show.getLastYearAired() == currentYear) {
                            mostRecentContent.add(c);
                        }
                    }
                    if (mostRecentContent.size() == 6) {
                        break;
                    }
                }
            }
        }

        return mostRecentContent;
    }

    @GetMapping({"/contentlist", "/contentlist/"})
    public String contentList(Model model) {
        Iterable<Content> allContent = contentRepository.findAll();
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

        logger.info(String.format("Contentlistfilter -- type:%s", contentType));
        logger.info(String.format("contentlistFilter -- genre:%s", genre));
        logger.info(String.format("contentlistFilter -- platform:%s", platform));
        logger.info(String.format("contentlistFilter -- keyword:%s", keyword));
        logger.info(String.format("contentlistFilter -- minScore:%d", minScore));


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

    @GetMapping({"/contentdetails","/contentdetails/{id}"})
    public String contentDetails(Model model, @PathVariable(required = false) Integer id) {
        if(id == null) return "contentdetails";
        Optional<Content> content = contentRepository.findById(id);

//        Eenvoudig reviews opvragen van film:
//        Set<Review> reviews = content.get().getReviews();
//        Set<Actor> cast = content.get().getCast();

        if(content.isPresent()) {
            Optional<Content> prevContent = contentRepository.findFirstByIdLessThanOrderByIdDesc(id);
            if(prevContent.isEmpty()) prevContent = contentRepository.findFirstByOrderByIdDesc();

            Optional<Content> nextContent = contentRepository.findFirstByIdGreaterThanOrderByIdAsc(id);
            if(nextContent.isEmpty()) nextContent = contentRepository.findFirstByOrderByIdAsc();

            model.addAttribute("content",content.get());
            model.addAttribute("prevContent", prevContent.get().getId());
            model.addAttribute("nextContent", nextContent.get().getId());
        }

        return "contentdetails";
    }



}
