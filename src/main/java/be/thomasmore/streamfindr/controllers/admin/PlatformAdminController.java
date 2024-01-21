package be.thomasmore.streamfindr.controllers.admin;
import be.thomasmore.streamfindr.model.Content;
import be.thomasmore.streamfindr.model.Platform;
import be.thomasmore.streamfindr.repositories.PlatformRepository;
import be.thomasmore.streamfindr.services.GoogleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class PlatformAdminController {
    @Autowired
    private PlatformRepository platformRepository;
    @Autowired
    private GoogleService googleService;

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
    public String platformNew() {
        return "admin/platformnew";
    }

    @PostMapping("/platformnew")
    public String platformNewPost(Model model,
                                  @Valid Platform platform,
                                  BindingResult bindingResult,
                                  @RequestParam(required = false) MultipartFile image) throws IOException {
        if(bindingResult.hasErrors()) {
            return "admin/platformnew";
        }

        if (image != null && !image.isEmpty()) {
            if (image.getSize() > 5 * 1024 * 1024) {
                bindingResult.reject("file.size.exceeded", "File size exceeded (max:5MB)");
                model.addAttribute("fileSizeExceededMessage","File size exceeded (max: 5MB)");
                return "admin/platformnew";
            }
            platform.setImageUrl(uploadImage(image));
        }


        Platform newPlatform = platformRepository.save(platform);
        return "redirect:/platformdetails/" + newPlatform.getId();
    }

    private String uploadImage(MultipartFile multipartFile) throws IOException {
        final String filename = multipartFile.getOriginalFilename();
        final File fileToUpload = new File(filename);
        FileOutputStream fos = new FileOutputStream(fileToUpload);
        fos.write(multipartFile.getBytes());
        fos.close();
        final String urlInFirebase = googleService.toFirebase(fileToUpload, filename);
        fileToUpload.delete();
        return urlInFirebase;
    }

}
