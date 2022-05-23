package eus.evernature.evern.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import eus.evernature.evern.models.Expert;

public interface ExpertRepository extends JpaRepository<Expert, Integer> {
    Expert findByUsername(String username);
    
    Expert findByEmail(String email);

    Expert findByresetPasswordToken(String resetPasswordToken);

    Expert findByactivateAccountToken(String activateAccountToken);

    Boolean existsByUsername(String username);

}
