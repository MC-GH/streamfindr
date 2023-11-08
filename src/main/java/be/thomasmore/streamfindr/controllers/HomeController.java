package be.thomasmore.streamfindr.controllers;

import be.thomasmore.streamfindr.model.Content;
import be.thomasmore.streamfindr.model.Movie;
import be.thomasmore.streamfindr.model.Show;
import be.thomasmore.streamfindr.repositories.ContentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

import java.util.stream.StreamSupport;

@Controller
public class HomeController {

    @Autowired
    private ContentRepository contentRepository;

    @GetMapping({"/", "/home"})
    public String home(Model model) {

        Iterable<Content> allContent = contentRepository.findAll();

        //StreamSupport used to create Stream, Spliterator used to create Stream from Iterator
        //as Iterator itself does not have a stream() method.
        List<Content> list = StreamSupport.stream(allContent.spliterator(),false).sorted(Comparator.comparing(Content::getName)).toList();

        List<Content> mostRecentContent = new ArrayList<>();
        mostRecentContent = findMostRecent(list);
        model.addAttribute("content", mostRecentContent);
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
