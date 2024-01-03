package be.thomasmore.streamfindr.controllers.admin;

import be.thomasmore.streamfindr.model.Content;
import be.thomasmore.streamfindr.model.Platform;
import be.thomasmore.streamfindr.repositories.PlatformRepository;
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
public class PlatformAdminController {
    private Logger logger = LoggerFactory.getLogger(ContentAdminController.class);
    @Autowired
    private PlatformRepository platformRepository;

    @ModelAttribute("platform")
    public Platform findPlatform(@PathVariable(required = false) Integer id) {
        if(id == null) return new Platform();

        Optional<Platform> optionalPlatform = platformRepository.findById(id);
        return optionalPlatform.orElse(null);
    }

    @GetMapping({"/platformedit/{id}", "/platformedit", "/platformedit/"})
    public String platformEdit(@PathVariable(required = false) Integer id,
                               @ModelAttribute(name = "platform") Platform platform) {
        return "admin/platformedit";
    }

    @PostMapping("/platformedit/{id}")
    public String platformEditPost(@PathVariable Integer id,
                                   @Valid Platform platform,
                                   BindingResult bindingResult,
                                   @RequestParam(name = "clearCatalogue", defaultValue = "false") boolean clearCatalogue) {

        if(bindingResult.hasErrors()) return "admin/platformedit";

        if(clearCatalogue) {
            Set<Content> contentToRemove = platform.getContent();
            //Remove from content entity
            contentToRemove.forEach(content -> content.getPlatforms().remove(platform));
            //Remove from platform entity
            platform.getContent().clear();
        }

        platformRepository.save(platform);
        return "redirect:/platformdetails/" + id;
    }

    @GetMapping("/platformnew")
    public String platformNew(Model model) {
        return "admin/platformnew";
    }

}
