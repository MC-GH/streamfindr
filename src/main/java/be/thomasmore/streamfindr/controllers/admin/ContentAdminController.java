package be.thomasmore.streamfindr.controllers.admin;

import be.thomasmore.streamfindr.model.Content;
import be.thomasmore.streamfindr.model.Review;
import be.thomasmore.streamfindr.repositories.ActorRepository;
import be.thomasmore.streamfindr.repositories.ContentRepository;
import be.thomasmore.streamfindr.repositories.PlatformRepository;
import be.thomasmore.streamfindr.repositories.ReviewRepository;
import be.thomasmore.streamfindr.services.GoogleService;
import jakarta.validation.Valid;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PreDestroy;
import javax.naming.Binding;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class ContentAdminController {
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private PlatformRepository platformRepository;
    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private GoogleService googleService;

    private void addModelAttributes(Model model) {
        model.addAttribute("allPlatforms", platformRepository.findAll());
        model.addAttribute("allActors", actorRepository.findAll());
        model.addAttribute("allGenres", contentRepository.findDistinctGenres());
    }

    @ModelAttribute("content")
    public Content findContent(@PathVariable(required = false) Integer id) {
        if (id == null) return new Content();

        Optional<Content> optionalContent = contentRepository.findById(id);
        return optionalContent.orElse(null);
    }

    @GetMapping({"/contentedit/{id}", "/contentedit", "/contentedit/"})
    public String contentEdit(Model model,
                              @PathVariable(required = false) Integer id) {
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
                                 BindingResult bindingResult,
                                 @RequestParam(required = false) MultipartFile image) throws IOException {

        if (bindingResult.hasErrors()) {
            addModelAttributes(model);
            return "admin/contentnew";
        }

        if (image != null && !image.isEmpty()) {
            if (image.getSize() > 5 * 1024 * 1024) {
                addModelAttributes(model);
                bindingResult.reject("file.size.exceeded", "File size exceeded (max:5MB)");
                return "admin/contentnew";
            }

            try {
                content.setImageUrl(uploadImage(image));
            } catch (MaxUploadSizeExceededException e) {
                addModelAttributes(model);
                bindingResult.reject("file.size.exceeded", "File size exceeded (max:5MB)");
                return "admin/contentnew";
            }
        }

        Content newContent = contentRepository.save(content);
        return "redirect:/contentdetails/" + newContent.getId();
    }


    private String uploadImage(MultipartFile multipartFile) throws IOException {
        final String filename = multipartFile.getOriginalFilename();
        final File fileToUpload = new File(filename);
        FileOutputStream fos = new FileOutputStream(fileToUpload);
        fos.write(multipartFile.getBytes());
        final String urlInFirebase = googleService.toFirebase(fileToUpload, filename);
        fileToUpload.delete();
        return urlInFirebase;
    }

}
