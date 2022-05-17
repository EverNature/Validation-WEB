package eus.evernature.evern.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
  
  @GetMapping
  public String login(HttpServletRequest request, HttpServletResponse response) {
    return "login";
  }
  
}
