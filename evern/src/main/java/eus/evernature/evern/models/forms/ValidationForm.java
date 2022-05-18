package eus.evernature.evern.models.forms;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class ValidationForm {
    
    @NotEmpty
    Integer id;

    boolean selection;

    String correctedClass;

    String newClass;




    
}
