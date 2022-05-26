package eus.evernature.evern.models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import eus.evernature.evern.models.forms.ValidationForm;

@SpringBootTest
class ValidationFormTest {

    @Test
    void validationFormEqualsIsOk() throws Exception {
        ValidationForm validationForm = new ValidationForm();
        validationForm.setAnimal("animal");
        validationForm.setNewClass("newClass");
        validationForm.setSelection(true);

        assertTrue("Equals not working", validationForm.equals(validationForm));
    }

    @Test
    void validationFormEqualsIsnOk() throws Exception {
        ValidationForm validationForm = new ValidationForm();
        validationForm.setAnimal("animal");
        validationForm.setNewClass("newClass");
        validationForm.setSelection(true);

        ValidationForm validationForm2 = new ValidationForm();
        validationForm2.setAnimal("animal");
        validationForm2.setNewClass("newClass");
        validationForm2.setSelection(false);

        assertFalse("Equals not working", validationForm.equals(validationForm2));

        validationForm2.setNewClass("a");
        assertFalse("Equals not working", validationForm.equals(validationForm2));

        validationForm2.setAnimal("a");
        assertFalse("Equals not working", validationForm.equals(validationForm2));
        
    }

    @Test
    void validationFormHashCodeIsOk() throws Exception {
        ValidationForm validationForm = new ValidationForm();
        validationForm.setAnimal("animal");
        validationForm.setNewClass("newClass");
        validationForm.setSelection(true);

        assertEquals(validationForm.hashCode(), validationForm.hashCode());
    }

    @Test
    void validationFormTostringIsOk() throws Exception {
        ValidationForm validationForm = new ValidationForm();
        validationForm.setAnimal("animal");
        validationForm.setNewClass("newClass");
        validationForm.setSelection(true);

        assertEquals(validationForm.toString(), validationForm.toString());
    }
}
