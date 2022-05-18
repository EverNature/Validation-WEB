package eus.evernature.evern.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eus.evernature.evern.models.Animal;
import eus.evernature.evern.models.Prediction;

@Controller
@RequestMapping("/validate")
public class ValidationController {
    
    @GetMapping("/{id}")
    public String validate(Model model, String id) {

        Integer idInt = Integer.parseInt(id);

        model.addAttribute("validationAnimal", new Prediction(1, null, new Animal(1, "Great kiskadee", true, null, null), null, 0.9f, "mensahe", "/images/aaaaaaaaa.jpg", true));
        
        return "validation";
    }
}
