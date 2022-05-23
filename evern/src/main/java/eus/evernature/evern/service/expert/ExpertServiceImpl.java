package eus.evernature.evern.service.expert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eus.evernature.evern.exception.UserNotFoundException;
import eus.evernature.evern.models.Expert;
import eus.evernature.evern.models.Role;
import eus.evernature.evern.models.Specialization;
import eus.evernature.evern.models.forms.ExpertCreationForm;
import eus.evernature.evern.repository.ExpertRepository;
import eus.evernature.evern.repository.RoleRepository;
import eus.evernature.evern.repository.SpecializationRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@NoArgsConstructor
@Transactional
@Slf4j
public class ExpertServiceImpl implements ExpertService, UserDetailsService {

    @Autowired
    private ExpertRepository expertRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SpecializationRepository specializationRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Expert expert = expertRepository.findByUsername(username);

        if (expert == null) {
            log.error("User not found in the database instance");
            throw new UsernameNotFoundException("User not found in database instance");
        } else {
            log.info("User found in database: {}", username);
        }

        if(!expert.getAccountEnabled()) {
            log.error("Account not activated");
            throw new UsernameNotFoundException("The account is not activated yet");
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        expert.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

        return new User(expert.getUsername(), expert.getPassword(), authorities);
    }

    @Override
    public Expert saveUser(Expert expert) {
        log.info("Saving new user to the database");
        expert.setPassword(passwordEncoder.encode(expert.getPassword()));
        return expertRepository.save(expert);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        Expert expert = expertRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        expert.getRoles().add(role);
    }

    @Override
    public Expert getExpert(String username) {
        log.info("Getting user {} from database", username);
        return expertRepository.findByUsername(username);
    }

    @Override
    public List<Expert> getExperts() {
        log.info("Fetching all users from database");
        return expertRepository.findAll();
    }

    @Override
    public Expert getExpertByEmail(String email) {

        // if (mailFormatCheck(email)) {
        //     log.error("Invalid email. Not matching regex pattern. Email: {}", email);
        //     return null;
        // }

        Expert expert = expertRepository.findByEmail(email);

        System.out.println("El email es: " + email);

        if(expert == null) log.error("Expert not found with email: {}", email);
        
        return expert;
    }

    public static boolean mailFormatCheck(String emailAddress) {
        String regexPattern = "^(.+)@(\\S+)$";

        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    @Override
    public Expert getExpertByResetPasswordToken(String token) {
        return expertRepository.findByresetPasswordToken(token);
    }

    @Override
    public Expert getExpertByActivateAccountToken(String token) {
        return expertRepository.findByactivateAccountToken(token);
    }

    @Override
    public void updatePassword(Expert expert, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        
        expert.setPassword(encodedPassword);
        expert.setResetPasswordToken(null);

        expertRepository.save(expert);
    }

    @Override
    public void updateResetPasswordToken(String token, String email) throws UserNotFoundException {

        Expert expert = getExpertByEmail(email);

        if(expert == null) {
            log.error("Expert not found, revoking generated token");
            throw new UserNotFoundException("User not found");
        }

        expert.setResetPasswordToken(token);
        expertRepository.save(expert);
    }

    @Override
    public Expert mapExpertFormToExpert(ExpertCreationForm expertForm) {
        Expert expert = new Expert();
        
        Specialization specialization = specializationRepository.findByname(expertForm.getSpecialization());

        expert.setUsername(expertForm.getUsername());
        expert.setEmail(expertForm.getEmail());
        expert.setSurname(expertForm.getSurmane());
        expert.setPassword(passwordEncoder.encode(expert.getPassword()));
        expert.setSpecialization(specialization);

        return expert;
    }

    @Override
    public boolean checkExpertExistent(String username) {
        return expertRepository.existsByUsername(username);
    }

    @Override
    public void addActivationToken(Expert expert, String token) {
        expert.setActivateAccountToken(token);

        expert = expertRepository.save(expert);        
    }

    @Override
    public void setAccountEnabled(Expert expert, boolean b) {
        expert.setAccountEnabled(b);

        expertRepository.save(expert);
    }

}
