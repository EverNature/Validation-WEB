package eus.evernature.evern.service.validation;

import eus.evernature.evern.models.Expert;
import eus.evernature.evern.models.forms.ValidationForm;

public interface ValidationService {
    void saveValidation(ValidationForm validationForm, Expert expert, Integer validationId);
}