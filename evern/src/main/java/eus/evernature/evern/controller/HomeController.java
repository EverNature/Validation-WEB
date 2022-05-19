package eus.evernature.evern.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eus.evernature.evern.service.prediction.PredictionService;

@Controller
@RequestMapping("/home")
public class HomeController {

  @Autowired
  PredictionService predictionService;

  @GetMapping()
  public String getHome(Model model) {
        
    model.addAttribute("predictions", predictionService.getPredictions());

    return "home";
  }
}