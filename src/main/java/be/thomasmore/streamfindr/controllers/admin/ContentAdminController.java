package be.thomasmore.streamfindr.controllers.admin;

import be.thomasmore.streamfindr.model.Content;
import be.thomasmore.streamfindr.model.Review;
import be.thomasmore.streamfindr.repositories.ActorRepository;
import be.thomasmore.streamfindr.repositories.ContentRepository;
import be.thomasmore.streamfindr.repositories.PlatformRepository;
import be.thomasmore.streamfindr.repositories.ReviewRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

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
    @Autowired
    private ReviewRepository reviewRepository;


    @ModelAttribute("content")
    public Content findContent(@PathVariable(required = false) Integer id) {
        if (id == null) return new Content();

        Optional<Content> optionalContent = contentRepository.findById(id);
        return optionalContent.orElse(null);

    }

    @GetMapping({"/contentedit/{id}", "/contentedit", "/contentedit/"})
    public String contentEdit(Model model,
                              @PathVariable(required = false) Integer id) {
        //Data ophalen om te gebruiken in select fields in form
        addModelAttributes(model);
        return "admin/contentedit";
    }

    @PostMapping("/contentedit/{id}")
    public String contentEditPost(Model model,
                                  @PathVariable Integer id,
                                  @Valid Content content,
                                  BindingResult bindingResult,
                                  @RequestParam(name = "clearReviews", defaultValue = "false") boolean clearReviews) {

        if (bindingResult.hasErrors()) {
            addModelAttributes(model);
            return "admin/contentedit";
        }

        if (clearReviews) {
            Set<Review> reviewsToRemove = content.getReviews();
            content.setReviews(null);
            reviewRepository.deleteAll(reviewsToRemove);
        }

        contentRepository.save(content);
        return "redirect:/contentdetails/" + id;

    }

    @GetMapping("/contentnew")
    public String contentNew(Model model) {
        addModelAttributes(model);
        return "admin/contentnew";
    }

    @PostMapping("/contentnew")
    public String contentNewPost(Model model,
                                 @Valid Content content,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            addModelAttributes(model);
            return "admin/contentnew";
        }

        Content newContent = contentRepository.save(content);
        return "redirect:/contentdetails/" + newContent.getId();

    }

    private void addModelAttributes(Model model) {
        model.addAttribute("allPlatforms", platformRepository.findAll());
        model.addAttribute("allActors", actorRepository.findAll());
        model.addAttribute("allGenres", contentRepository.findDistinctGenres());
    }
}
