package eus.evernature.evern.service.validation;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.evernature.evern.models.Animal;
import eus.evernature.evern.models.Expert;
import eus.evernature.evern.models.Prediction;
import eus.evernature.evern.models.forms.ValidationForm;
import eus.evernature.evern.service.animal.AnimalService;
import eus.evernature.evern.service.prediction.PredictionService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@NoArgsConstructor
@Transactional
@Slf4j
public class ValidationServiceImpl implements ValidationService {

    @Autowired
    PredictionService predictionService;

    @Autowired
    AnimalService animalService;

    @Override
    public void saveValidation(ValidationForm validationForm, Expert expert, Integer validationId) {
        Prediction pred = predictionService.getPrediction(validationId);

        pred.setCorrectorExpert(expert);
        predictionService.savePrediction(pred);

        if (validationForm.isSelection()) {
            log.info("Validation validated from validator: {} is correct", expert.getUsername());
            saveCorrectValidation(pred);
            return;
        }

        if (!validationForm.getAnimal().equals("other")) {
            log.info("Validation validated from validator: {} is not correct and has an existent animal", expert.getUsername());
            saveIncorrectValidationWithExistentAnimal(pred, validationForm);
            return;
        }

        log.info("Validation validated from validator: {} is not correct and has an inexistent animal", expert.getUsername());
        saveIncorrectValidationWithInexistentAnimal(pred, validationForm);
    }

    private void saveCorrectValidation(Prediction pred) {
        pred.setIsCorrect(true);
        predictionService.savePrediction(pred);
    }

    private void saveIncorrectValidationWithExistentAnimal(Prediction pred, ValidationForm validationForm) {
        pred.setIsCorrect(false);
        Animal correctedAnimal = animalService.getAnimal(validationForm.getAnimal());

        pred.setCorrectedAnimal(correctedAnimal);
        predictionService.savePrediction(pred);
    }

    private void saveIncorrectValidationWithInexistentAnimal(Prediction pred, ValidationForm validationForm) {
        Animal newAnimal = new Animal();
        newAnimal.setName(validationForm.getNewClass());
        newAnimal = animalService.saveAnimal(newAnimal);

        pred.setCorrectedAnimal(newAnimal);
        predictionService.savePrediction(pred);
    }
}
