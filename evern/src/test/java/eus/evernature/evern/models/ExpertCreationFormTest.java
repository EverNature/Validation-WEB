package eus.evernature.evern.models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import eus.evernature.evern.models.forms.ExpertCreationForm;

@SpringBootTest
class ExpertCreationFormTest {

    @Test
    void expertCreationFormEqualsIsOk() throws Exception {
        ExpertCreationForm expertCreationForm = new ExpertCreationForm();
        expertCreationForm.setEmail("a");
        expertCreationForm.setName("a");
        expertCreationForm.setPassword("a");
        expertCreationForm.setSurname("a");
        expertCreationForm.setName("a");
        expertCreationForm.setUsername("a");
        expertCreationForm.setSpecialization("a");

        assertTrue("Equals not working", expertCreationForm.equals(expertCreationForm));

        ExpertCreationForm expertCreationForm2 = new ExpertCreationForm();
        expertCreationForm2.setEmail("a");
        expertCreationForm2.setName("a");
        expertCreationForm2.setPassword("a");
        expertCreationForm2.setSurname("a");
        expertCreationForm2.setName("a");
        expertCreationForm2.setSpecialization("a");
        expertCreationForm2.setUsername("a");

        assertTrue("Equals not working", expertCreationForm.equals(expertCreationForm2));
    }

    @Test
    void expertCreationFormEqualsIsNok() throws Exception {
        ExpertCreationForm expertCreationForm = new ExpertCreationForm();
        expertCreationForm.setEmail("a");
        expertCreationForm.setName("a");
        expertCreationForm.setPassword("a");
        expertCreationForm.setSurname("a");
        expertCreationForm.setName("a");
        expertCreationForm.setSpecialization("a");
        expertCreationForm.setUsername("a");

        ExpertCreationForm expertCreationForm2 = new ExpertCreationForm();
        expertCreationForm2.setEmail("a");
        expertCreationForm2.setName("a");
        expertCreationForm2.setPassword("a");
        expertCreationForm2.setSurname("a");
        expertCreationForm2.setName("a");
        expertCreationForm2.setSpecialization("a");
        expertCreationForm2.setUsername("b");

        assertFalse("Equals not working", expertCreationForm.equals(expertCreationForm2));

        expertCreationForm2.setUsername("b");
        assertFalse("Equals not working", expertCreationForm.equals(expertCreationForm2));

        expertCreationForm2.setUsername("b");
        assertFalse("Equals not working", expertCreationForm.equals(expertCreationForm2));

        expertCreationForm2.setSpecialization("b");
        assertFalse("Equals not working", expertCreationForm.equals(expertCreationForm2));

        expertCreationForm2.setPassword("b");
        assertFalse("Equals not working", expertCreationForm.equals(expertCreationForm2));

        expertCreationForm2.setName("b");
        assertFalse("Equals not working", expertCreationForm.equals(expertCreationForm2));

        expertCreationForm2.setEmail("b");
        assertFalse("Equals not working", expertCreationForm.equals(expertCreationForm2));
    }

    @Test
    void expertCreationFormHashCodeIsOk() throws Exception {
        ExpertCreationForm expertCreationForm = new ExpertCreationForm();
        expertCreationForm.setEmail("a");
        expertCreationForm.setName("a");
        expertCreationForm.setPassword("a");
        expertCreationForm.setSurname("a");
        expertCreationForm.setName("a");
        expertCreationForm.setUsername("a");
        expertCreationForm.setSpecialization("a");

        assertEquals(expertCreationForm.hashCode(), expertCreationForm.hashCode());
    }

    @Test
    void expertCreationFormTostringIsOk() throws Exception {
        ExpertCreationForm expertCreationForm = new ExpertCreationForm();
        expertCreationForm.setEmail("a");
        expertCreationForm.setName("a");
        expertCreationForm.setPassword("a");
        expertCreationForm.setSurname("a");
        expertCreationForm.setName("a");
        expertCreationForm.setUsername("a");
        expertCreationForm.setSpecialization("a");

        assertEquals(expertCreationForm.toString(), expertCreationForm.toString());
    }
}
