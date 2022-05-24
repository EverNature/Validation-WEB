package eus.evernature.evern.models.forms;

import lombok.Data;

@Data
public class ExpertCreationForm {
    String username;
    String name;
    String surmane;
    String email;
    String specialization;
    String password;
}
