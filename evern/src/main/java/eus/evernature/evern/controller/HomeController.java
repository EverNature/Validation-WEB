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

    // Prediction prediction = new Prediction(1, null, new Animal(1, "ANIMAL", true, null, null), null, 0.9f, "mensahe", "/images/aaaaaaaaa.jpg", true);
    // Prediction prediction2 = new Prediction(1, null, new Animal(1, "ANIMAL", true, null, null), null, 0.9f, "mensahe", "/images/aaaaaaaaa.jpg", true);
    // Prediction prediction3 = new Prediction(1, null, new Animal(1, "ANIMAL", true, null, null), null, 0.9f, "mensahe", "/images/aaaaaaaaa.jpg", true);
    
    // ArrayList<Prediction> predictions = new ArrayList<Prediction>();
    // predictions.add(prediction);
    // predictions.add(prediction2);
    // predictions.add(prediction3);
    
    model.addAttribute("predictions", predictionService.getPredictions());

    return "home";
  }
}