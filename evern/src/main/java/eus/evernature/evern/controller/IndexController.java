package eus.evernature.evern.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({ "", "/" })
public class IndexController {

    
    /** 
     * Esta función se encarga de mostrar la página de inicio
     * @return String   El nombre de la vista
     */
    @GetMapping()
    public String getHome() {
        return "redirect:/home";
    }
}