package be.thomasmore.streamfindr.controllers;

import be.thomasmore.streamfindr.model.Account;
import be.thomasmore.streamfindr.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping({"/accountdetails", "/accountdetails/", "accountdetails/{id}"})
    public String accountDetails(Model model,
                                 @PathVariable(required = false) Integer id) {

        if(id==null) return "user/profile";

        Optional<Account> optionalAccount = accountRepository.findById(id);
        if(optionalAccount.isPresent()) {
            model.addAttribute("account",optionalAccount.get());
        }
        return "user/profile";
    }
}
