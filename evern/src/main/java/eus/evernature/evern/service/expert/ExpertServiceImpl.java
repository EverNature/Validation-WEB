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
import eus.evernature.evern.repository.ExpertRepository;
import eus.evernature.evern.repository.RoleRepository;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Expert expert = expertRepository.findByUsername(username);

        if (expert == null) {
            log.error("User not found in the database instance");
            throw new UsernameNotFoundException("User not found in database instance");
        } else {
            log.info("User found in database: {}", username);
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

        if (mailFormatCheck(email)) {
            log.error("Invalid email. Not matching regex pattern. Email: {}", email);
            return null;
        }

        Expert expert = expertRepository.findByEmail(email);

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
}
