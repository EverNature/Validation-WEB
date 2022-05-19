package eus.evernature.evern.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eus.evernature.evern.models.Expert;
import eus.evernature.evern.service.expert.ExpertService;
import eus.evernature.evern.service.mail.MailService;
import eus.evernature.evern.service.urlService.UrlService;
import net.bytebuddy.utility.RandomString;

@Controller
@RequestMapping("/login/account-recovery")
public class ForgotPasswordController {

    @Autowired
    ExpertService expertService;

    @Autowired
    UrlService urlService;

    @Autowired
    MailService mailService;

    @GetMapping
    public String getAccountRecoveryPage() {
        return "recovery";
    }

    @PostMapping
    public String generatePasswordToken(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        String token = RandomString.make(45);

        
        try {
            expertService.updateResetPasswordToken(token, email);
        } catch (Exception e) {
            model.addAttribute("error", "El email no se ha encontrado");
            return "redirect:/";
        }
        
        // generate email
        String resetPasswordLink = urlService.getSiteUrl(request) + "/reset_password?token=" + token;
        
        System.out.println(resetPasswordLink);
        
        // send email
        try {
            mailService.sendEmail(email, resetPasswordLink, "Password reset", "<p>Usa el siguiente link para reiniciar tu contraseña: <a href='"+resetPasswordLink+"'>link de contraseña</a></p>");
        } catch (Exception e) {
            model.addAttribute("error", "Ha ocurrido un problema a la hora de enviar el email");
            return "redirect:/";
        }

        return "redirect:/login";
    }

    @PostMapping("/recover")
    public String getAccountRecoveryPage(String token, String email, Model model) {
        Expert expert = expertService.getExpertByEmail(email);

        if (expert == null) {
            model.addAttribute("error", "El email introducido no se ha encontrado");
            return "redirect:/";
        }

        expert.setResetPasswordToken(token);
        expertService.saveUser(expert);

        return "recovery";
    }

    @GetMapping
    public String resetPassword() {
        return "change_password";
    }

}
