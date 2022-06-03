package eus.evernature.evern.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import eus.evernature.evern.service.expert.ExpertService;

@Controller
public class LoginController {

    @Autowired
    ExpertService expertService;

    
    /** 
     * Esta función se encarga de mostrar la página de inicio de sesion
     * @param model
     * @return String   El nombre de la vista
     */
    @GetMapping("/login")
    public String login(Model model) {
        if (checkLogedIn()) {
            return "redirect:/";
        }
        model.addAttribute("user", new User());

        return "login";
    }

    
    /** 
     * Esta función se encarga de validar si el usuario esta logueado o no
     * @return boolean  True si el usuario esta logueado, false en caso contrario
     */
    private boolean checkLogedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return !(authentication == null || authentication instanceof AnonymousAuthenticationToken);
    }
}