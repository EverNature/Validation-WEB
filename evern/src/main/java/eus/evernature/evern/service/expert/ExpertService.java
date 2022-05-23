package eus.evernature.evern.service.expert;

import java.util.List;

import eus.evernature.evern.models.Expert;
import eus.evernature.evern.models.forms.ExpertCreationForm;

public interface ExpertService {
    Expert saveUser(Expert expert);
    void addRoleToUser(String username, String roleName);
    Expert getExpert(String username);
    Expert getExpertByEmail(String email);
    Expert getExpertByResetPasswordToken(String token);
    Expert getExpertByActivateAccountToken(String token);
    List<Expert> getExperts();
    void updatePassword(Expert expert, String newPassword);
    void updateResetPasswordToken(String token, String email);

    Expert mapExpertFormToExpert(ExpertCreationForm expertForm);
    boolean checkExpertExistent(String username);
    void addActivationToken(Expert expert, String token);
    void setAccountEnabled(Expert expert, boolean b);
}
