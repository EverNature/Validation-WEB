package eus.evernature.evern.service.prediction;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.evernature.evern.models.Prediction;
import eus.evernature.evern.repository.PredictionRepository;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
@Transactional
public class PredictionServiceImpl implements PredictionService {

    @Autowired
    PredictionRepository predictionRepository;

    @Override
    public Prediction savePrediction(Prediction prediction) {
        return predictionRepository.save(prediction);
    }

    @Override
    public Prediction getPrediction(Integer predictionId) {
        return predictionRepository.getById(predictionId);
    }

    @Override
    public List<Prediction> getPredictions() {
        return predictionRepository.findAll();
    }

    @Override
    public Prediction updatePrediction(Integer predictionId, Prediction prediction) {
        Prediction pred = predictionRepository.getById(predictionId);
        pred = prediction;
        return predictionRepository.save(pred);
    }
}
