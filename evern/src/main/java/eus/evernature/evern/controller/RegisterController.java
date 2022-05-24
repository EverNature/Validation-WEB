package eus.evernature.evern.controller;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eus.evernature.evern.models.Expert;
import eus.evernature.evern.models.forms.ExpertCreationForm;
import eus.evernature.evern.service.expert.ExpertService;
import eus.evernature.evern.service.mail.MailService;
import eus.evernature.evern.service.specialization.SpecializationService;
import eus.evernature.evern.service.urlService.UrlService;
import net.bytebuddy.utility.RandomString;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    ExpertService expertService;

    @Autowired
    SpecializationService specializationService;

    @Autowired
    MailService mailService;

    @Autowired
    UrlService urlService;

    @GetMapping
    public String register(Model model) {

        model.addAttribute("specializations", specializationService.getAllSpecializations());

        return "register";
    }

    @PostMapping
    public String registerSubmit(ExpertCreationForm form, Model model, HttpServletRequest request) {
        
        Expert expert = expertService.mapExpertFormToExpert(form);
        
        if(expertService.checkExpertExistent(expert.getUsername())) {
            model.addAttribute("error", "El usuario que intentas crear ya existe");
            return "redirect:/register";
        }
        
        expert = expertService.saveUser(expert);

        String token = RandomString.make(45);

        expertService.addActivationToken(expert.getUsername(), token);

        // generate email
        String resetPasswordLink = urlService.getSiteUrl(request) + "/activate?token=" + token;

        System.out.println(resetPasswordLink);

        // send email
        try {
            mailService.sendEmail(expert.getEmail(), resetPasswordLink, "Activar cuenta",
                    "<p>Usa el siguiente link para activar la cuenta: <a href='" + resetPasswordLink
                            + "'>Link de activacion</a></p>");
        } catch (Exception e) {
            model.addAttribute("error", "Ha ocurrido un problema a la hora de enviar el email");
            return "redirect:/";
        }

        return "redirect:/login";
    }

    @GetMapping("/activate")
    public String submitToken(@PathParam("token") String token, Model model) {

        Expert expert = expertService.getExpertByActivateAccountToken(token);

        if (expert == null) {
            model.addAttribute("error", "Account not found");
            return "redirect:/login";
        }

        expertService.setAccountEnabled(expert.getUsername(), true);
        
        return "redirect:/login";
    }

}
