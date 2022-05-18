package eus.evernature.evern.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login/accountrecovery")
public class ForgotPasswordController {
    
    @GetMapping
    public String getAccountRecoveryPage() {
        return "recovery";
    }

    


}
