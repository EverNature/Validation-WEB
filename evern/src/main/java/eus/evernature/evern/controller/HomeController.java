package eus.evernature.evern.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import eus.evernature.evern.models.Prediction;
import eus.evernature.evern.service.prediction.PredictionService;

@Controller
@RequestMapping("/home")
public class HomeController {

  @Autowired
  PredictionService predictionService;
  


  @GetMapping({ "", "/{page}"})
  public String getHome(@PathVariable(required = false) Integer page, Model model) {

    if (page == null || page <= 0) page = 0;
    else page--;

    Page<Prediction> predictions = predictionService.getPredictionsSorted(page);

    model.addAttribute("predictions", predictions.getContent());
    model.addAttribute("actualPage", page);
    model.addAttribute("totalPages", predictions.getTotalPages());
        
    return "home";
  }
}