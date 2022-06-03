package eus.evernature.evern.controller;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    
    /** 
     * Esta función se encarga de mostrar el formulario de registro
     * @param model
     * @return String   El nombre de la vista
     */
    @GetMapping
    public String register(Model model) {

        model.addAttribute("specializations", specializationService.getAllSpecializations());
        model.addAttribute("expertCreationForm", new ExpertCreationForm());

        return "register";
    }

    
    /** 
     * Esta función se encarga de generar un token para la activacion de la cuenta y se encarga de enviar un correo de activación
     * @param form
     * @param result
     * @param model
     * @param request
     * @return String   El nombre de la vista
     */
    @PostMapping
    public String registerSubmit(@Validated @ModelAttribute ExpertCreationForm form, BindingResult result, Model model, HttpServletRequest request) {
        
        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return "redirect:/register";
        }

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

    
    /** 
     * Esta función se encarga de activar la cuenta del usuario recibiendo el token enviado por correo
     * @param token
     * @param model
     * @return String   El nombre de la vista
     */
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
