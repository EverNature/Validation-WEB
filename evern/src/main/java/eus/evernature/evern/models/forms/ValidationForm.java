package eus.evernature.evern.models.forms;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class ValidationForm {    
    @NotEmpty
    boolean selection;

    String animal;

    String newClass;
}
