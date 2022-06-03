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
import eus.evernature.evern.models.forms.UserRegistrationForm;
import eus.evernature.evern.service.expert.ExpertService;
import eus.evernature.evern.service.mail.MailService;
import eus.evernature.evern.service.urlService.UrlService;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;

@Controller
@RequestMapping("/login/account-recovery")
@Slf4j
public class ForgotPasswordController {

    static final String REDIRECT_LOGIN = "redirect:/login";

    @Autowired
    ExpertService expertService;

    @Autowired
    UrlService urlService;

    @Autowired
    MailService mailService;

    
    /** 
     * Esta función se encarga de mostrar el formulario de recuperación de contraseña
     * @return String   El nombre de la vista
     */
    @GetMapping
    public String getAccountRecoveryPage() {
        return "recovery";
    }

    
    /** 
     * Esta función se encarga de enviar un correo de recuperación de contraseña
     * @param request
     * @param model
     * @return String   El nombre de la vista
     */
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

        log.info("Reset password link: " + resetPasswordLink);

        // // send email
        // try {
        //     mailService.sendEmail(email, resetPasswordLink, "Password reset",
        //             "<p>Usa el siguiente link para reiniciar tu contraseña: <a href='" + resetPasswordLink
        //                     + "'>link de contraseña</a></p>");
        // } catch (Exception e) {
        //     model.addAttribute("error", "Ha ocurrido un problema a la hora de enviar el email");
        //     return "redirect:/";
        // }

        return REDIRECT_LOGIN;
    }

    
    /** 
     * Esta función obtiene el token de recuperación de contraseña y te muestra el formulario para cambiar la contraseña
     * @param token  El token de recuperación de contraseña
     * @param model
     * @return String   El nombre de la vista
     */
    @GetMapping("/reset_password")
    public String resetPassword(@PathParam("token") String token, Model model) {

        model.addAttribute("token", token);
        
        return "change_password";
    }

    
    /** 
     * Esta función se encarga de cambiar la contraseña siempre y cuando las contraseñas cumplan con los criterios de seguridad
     * @param form El formulario con la contraseña nueva
     * @param result El resultado de la validación teniendo en cuenta los criterios de seguridad
     * @param token El token de recuperación de contraseña
     * @param model
     * @return String   El nombre de la vista
     */
    @PostMapping("/reset_password")
    public String getAccountRecoveryPage(@Validated @ModelAttribute UserRegistrationForm form, BindingResult result, @PathParam("token") String token, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return "redirect:/login/account-recovery/reset_password?token=" + token;
        }

        Expert expert = expertService.getExpertByResetPasswordToken(token);

        if (expert == null) {
            model.addAttribute("error", "Account not found");
            return REDIRECT_LOGIN;
        }

        expertService.updatePassword(expert, form.getPassword());

        return REDIRECT_LOGIN;
    }
}
