package eus.evernature.evern.service.prediction;

import java.util.List;

import eus.evernature.evern.models.Prediction;

public interface PredictionService {
    Prediction savePrediction(Prediction prediction);
    Prediction getPrediction(Integer predictionId);
    List<Prediction> getPredictions();
    Prediction updatePrediction(Integer predictionId, Prediction prediction);
}
