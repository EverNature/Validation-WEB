package eus.evernature.evern.models.forms;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import eus.evernature.evern.validation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class ExpertCreationForm {

    @NotEmpty
    String username;

    @NotEmpty
    String name;

    @NotEmpty
    String surname;

    @NotEmpty
    @Email
    String email;

    @NotEmpty
    String specialization;

    @NotEmpty
    @ValidPassword
    String password;

}
