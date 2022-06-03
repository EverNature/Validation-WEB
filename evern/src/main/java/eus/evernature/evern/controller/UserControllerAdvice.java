package eus.evernature.evern.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import eus.evernature.evern.models.Expert;
import eus.evernature.evern.service.expert.ExpertService;


@ControllerAdvice
public class UserControllerAdvice {

  @Autowired
  ExpertService userService;

  
  /** 
   * Esta función se encarga de añadir el usuario logueado al modelo cada vez que se carga una vista
   * @return Expert  El usuario logueado
   */
  @ModelAttribute("user")
  public Expert addUserToModel() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    Expert user = null;
    if (auth != null)
      user = userService.getExpert(auth.getName());
    return user;
  }

}
