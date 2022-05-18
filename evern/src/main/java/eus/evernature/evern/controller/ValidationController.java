package eus.evernature.evern.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eus.evernature.evern.models.Expert;
import eus.evernature.evern.models.Prediction;
import eus.evernature.evern.models.forms.ValidationForm;
import eus.evernature.evern.service.animal.AnimalService;
import eus.evernature.evern.service.expert.ExpertService;
import eus.evernature.evern.service.prediction.PredictionService;
import eus.evernature.evern.service.validation.ValidationService;

@Controller
@RequestMapping("/validate")
public class ValidationController {

    @Autowired
    PredictionService predictionService;

    @Autowired
    ExpertService expertService;

    @Autowired
    AnimalService animalService;

    @Autowired
    ValidationService validationService;

    
    @GetMapping("/{id}")
    public String validatePage(Model model, @PathVariable("id") Integer id) {

        Prediction pred = predictionService.getPrediction(id);

        if(pred == null)  {
            model.addAttribute("error", "Predicton not found");
            return "redirect:/home";
        }
        
        model.addAttribute("validationAnimal", pred);
        
        return "validation";
    }

    @PostMapping("/{id}")
    public String validate(Model model, @PathVariable("id") Integer id, @Validated @ModelAttribute ValidationForm validationForm, BindingResult result) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        String username = authentication.getName();

        Expert expert = expertService.getExpert(username);

        if(result.hasErrors()) {
            model.addAttribute("error","Parameters not valid");
            return "redirect:/";
        }

        validationService.saveValidation(validationForm, expert, id);
        return "redirect:/home";
    }
}
