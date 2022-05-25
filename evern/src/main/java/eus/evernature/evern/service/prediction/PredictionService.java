package eus.evernature.evern.service.prediction;

import java.util.List;

import org.springframework.data.domain.Page;

import eus.evernature.evern.models.Prediction;

public interface PredictionService {
    Prediction savePrediction(Prediction prediction);
    Prediction getPrediction(Integer predictionId);
    List<Prediction> getPredictions();
    Page<Prediction> getPredictionsSorted(Integer page);
    Prediction updatePrediction(Integer predictionId, Prediction prediction);
}
