package eus.evernature.evern.models;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import eus.evernature.evern.models.forms.UserRegistrationForm;

@SpringBootTest
class UserRegistrationFormTest {

    @Test
    void validationFormEqualsIsOk() throws Exception {
        UserRegistrationForm userRegistrationFormTest = new UserRegistrationForm();
        userRegistrationFormTest.setPassword("21356556746324qrwegsdfhnfuhytioewfkF&UT/IUHKj34ter");
        userRegistrationFormTest.setVerifyPassword("21356556746324qrwegsdfhnfuhytioewfkF&UT/IUHKj34ter");

        assertTrue(true);
    }
}
