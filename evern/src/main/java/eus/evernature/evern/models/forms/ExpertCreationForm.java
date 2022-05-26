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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ExpertCreationForm other = (ExpertCreationForm) obj;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (specialization == null) {
            if (other.specialization != null)
                return false;
        } else if (!specialization.equals(other.specialization))
            return false;
        if (surmane == null) {
            if (other.surmane != null)
                return false;
        } else if (!surmane.equals(other.surmane))
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((specialization == null) ? 0 : specialization.hashCode());
        result = prime * result + ((surmane == null) ? 0 : surmane.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }  
}
