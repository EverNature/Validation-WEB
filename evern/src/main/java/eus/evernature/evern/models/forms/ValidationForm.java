package eus.evernature.evern.models.forms;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class ValidationForm {    
    @NotEmpty
    boolean selection;

    String animal;

    String newClass;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ValidationForm other = (ValidationForm) obj;
        if (animal == null) {
            if (other.animal != null)
                return false;
        } else if (!animal.equals(other.animal))
            return false;
        if (newClass == null) {
            if (other.newClass != null)
                return false;
        } else if (!newClass.equals(other.newClass))
            return false;
        if (selection != other.selection)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((animal == null) ? 0 : animal.hashCode());
        result = prime * result + ((newClass == null) ? 0 : newClass.hashCode());
        result = prime * result + (selection ? 1231 : 1237);
        return result;
    }

    
}
