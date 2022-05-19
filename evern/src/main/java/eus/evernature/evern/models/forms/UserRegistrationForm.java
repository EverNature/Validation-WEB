package eus.evernature.evern.models.forms;

import eus.evernature.evern.validation.ValidFieldsMatch;
import eus.evernature.evern.validation.ValidPassword;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ValidFieldsMatch(field = "password", fieldMatch = "verifyPassword")
public class UserRegistrationForm {

    @ValidPassword
    private String password;

    private String verifyPassword;

}