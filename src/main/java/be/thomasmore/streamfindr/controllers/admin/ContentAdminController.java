package be.thomasmore.streamfindr.controllers.admin;

import be.thomasmore.streamfindr.model.Actor;
import be.thomasmore.streamfindr.model.Content;
import be.thomasmore.streamfindr.model.Platform;
import be.thomasmore.streamfindr.repositories.ActorRepository;
import be.thomasmore.streamfindr.repositories.ContentRepository;
import be.thomasmore.streamfindr.repositories.PlatformRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class ContentAdminController {
    private Logger logger = LoggerFactory.getLogger(ContentAdminController.class);
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private PlatformRepository platformRepository;
    @Autowired
    private ActorRepository actorRepository;


    @ModelAttribute("content")
    public Content findContent(@PathVariable (required = false) Integer id) {
        logger.info("findContent" + id);
        if(id!=null) {
            Optional<Content> optionalContent = contentRepository.findById(id);
            if(optionalContent.isPresent()) return optionalContent.get();
        }
        return null;
    }
    @GetMapping("/contentedit/{id}")
    public String contentEdit(Model model,
                              @PathVariable(required = false) Integer id) {
        //Data ophalen om te gebruiken in select fields in form
        Iterable<Platform> allPlatforms = platformRepository.findAll();
        Iterable<Actor> allActors = actorRepository.findAll();
        List<String> allGenres = contentRepository.findDistinctGenres();

        model.addAttribute("allPlatforms", allPlatforms);
        model.addAttribute("allActors", allActors);
        model.addAttribute("allGenres", allGenres);
        return "admin/contentedit";
    }


}
