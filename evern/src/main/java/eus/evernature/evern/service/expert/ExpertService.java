package eus.evernature.evern.service.expert;

import java.util.List;

import eus.evernature.evern.models.Expert;

public interface ExpertService {
    Expert saveUser(Expert expert);
    void addRoleToUser(String username, String roleName);
    Expert getExpert(String username);
    Expert getExpertByEmail(String email);
    Expert getExpertByResetPasswordToken(String token);
    List<Expert> getExperts();
    void updatePassword(Expert expert, String newPassword);
    void updateResetPasswordToken(String token, String email);
}
