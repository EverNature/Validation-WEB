package eus.evernature.evern.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eus.evernature.evern.models.forms.UserRegistrationForm;

@Controller
@RequestMapping("/login/accountrecovery")
public class ForgotPasswordController {

    @GetMapping
    public String getAccountRecoveryPage() {
        return "recovery";
    }

    @GetMapping("/reset")
    public String getAccountRecoveryPageReset(Model model) {

        return "change_password";
    }

    @PostMapping("/reset")
    public String postAccountRecoveryPageReset(@Validated @ModelAttribute UserRegistrationForm form,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            System.out.println("Error");
            return "redirect:/login/accountrecovery/reset";
        }

        return "redirect:/login";
    }

}