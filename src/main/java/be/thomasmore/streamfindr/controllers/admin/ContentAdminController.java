package be.thomasmore.streamfindr.controllers.admin;

import be.thomasmore.streamfindr.model.Actor;
import be.thomasmore.streamfindr.model.Content;
import be.thomasmore.streamfindr.model.Platform;
import be.thomasmore.streamfindr.repositories.ActorRepository;
import be.thomasmore.streamfindr.repositories.ContentRepository;
import be.thomasmore.streamfindr.repositories.PlatformRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
        if(id!=null) {
            Optional<Content> optionalContent = contentRepository.findById(id);
            if(optionalContent.isPresent()) return optionalContent.get();
        }
        return null;
    }

    @GetMapping({"/contentedit/{id}","/contentedit","/contentedit/"})
    public String contentEdit(Model model,
                              @PathVariable(required = false) Integer id) {
        //Data ophalen om te gebruiken in select fields in form

        model.addAttribute("allPlatforms", platformRepository.findAll());
        model.addAttribute("allActors", actorRepository.findAll());
        model.addAttribute("allGenres", contentRepository.findDistinctGenres());
        return "admin/contentedit";
    }

    @PostMapping("/contentedit/{id}")
    public String contentEditPost(Model model,
                                  @PathVariable Integer id,
                                  @Valid Content content,
                                  BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            model.addAttribute("allPlatforms", platformRepository.findAll());
            model.addAttribute("allActors",actorRepository.findAll());
            model.addAttribute("allGenres", contentRepository.findDistinctGenres());
            return "admin/contentedit";
        }

        contentRepository.save(content);
        return "redirect:/contentdetails/" + id;

    }

}
