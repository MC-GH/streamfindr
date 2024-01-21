package be.thomasmore.streamfindr.controllers;
import be.thomasmore.streamfindr.model.Account;
import be.thomasmore.streamfindr.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;
import java.util.Optional;

@Controller
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping({"/accountdetails", "/accountdetails/"})
    public String accountDetails(Model model,
                                 Principal principal) {

        if(principal==null) return "redirect:/home";

        Optional<Account> optionalAccount = accountRepository.findByUsername(principal.getName());

        if(optionalAccount.isPresent()) {
            model.addAttribute("account",optionalAccount.get());
        }
        return "user/profile";
    }
}
