package eus.evernature.evern.service.prediction;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    
    /** 
     * Esta función guarda una predicción en la base de datos
     * @param prediction
     * @return Prediction
     */
    @Override
    public Prediction savePrediction(Prediction prediction) {
        return predictionRepository.save(prediction);
    }

    
    /** 
     * Esta función carga una predicción de la base de datos
     * @param predictionId
     * @return Prediction
     */
    @Override
    public Prediction getPrediction(Integer predictionId) {
        return predictionRepository.getById(predictionId);
    }

    
    /** 
     * Esta función carga todas las predicciones de la base de datos
     * @return List<Prediction>
     */
    @Override
    public List<Prediction> getPredictions() {
        List<Prediction> predictions = predictionRepository.findAll();
        return predictions == null ? new ArrayList<>() : predictions;
    }

    
    /** 
     * Esta función actualiza una predicción de la base de datos
     * @param predictionId
     * @param prediction
     * @return Prediction
     */
    @Override
    public Prediction updatePrediction(Integer predictionId, Prediction prediction) {
        prediction.setId(predictionId);

        return predictionRepository.existsById(predictionId) ? predictionRepository.save(prediction) : null;
    }

    
    /** 
     * Esta función devuelve todas las predicciones divididas en páginas
     * @param page
     * @return Page<Prediction>
     */
    @Override
    public Page<Prediction> getPredictionsSorted(Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        
        return predictionRepository.findAllSorted(pageable);
   }
}
