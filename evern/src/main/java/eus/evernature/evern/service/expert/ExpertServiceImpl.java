package eus.evernature.evern.service.expert;

import java.util.ArrayList;
import java.util.Collection;

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

    
    /** 
     * Esta función carga un experto de la base de datos, siempre y cuando exista y la cuanta este activada
     * @param username
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Expert expert = expertRepository.findByUsername(username);

        if (expert == null || !expert.getAccountEnabled()) {
            log.error("User not found in the database instance");
            throw new UsernameNotFoundException("User not found in database instance");
        } else {
            log.info("User found in database: {}", username);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        expert.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

        return new User(expert.getUsername(), expert.getPassword(), authorities);
    }

    
    /** 
     * Esta función crea un nuevo experto en la base de datos
     * @param expert
     * @return Expert
     */
    @Override
    public Expert saveUser(Expert expert) {
        log.info("Saving new user to the database");
        expert.setPassword(passwordEncoder.encode(expert.getPassword()));
        return expertRepository.save(expert);
    }

    
    /** 
     * Esta función actualiza un experto en la base de datos, añadiendole un nuevo rol
     * @param username
     * @param roleName
     */
    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        Expert expert = expertRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        expert.getRoles().add(role);
    }

    
    /** 
     * Esta función obtiene un experto de la base de datos, a partir de su username
     * @param username
     * @return Expert
     */
    @Override
    public Expert getExpert(String username) {
        log.info("Getting user {} from database", username);
        return expertRepository.findByUsername(username);
    }

    
    /** 
     * Esta función obtiene un experto de la base de datos, a partir de su email
     * @param email
     * @return Expert
     */
    @Override
    public Expert getExpertByEmail(String email) {
    
        Expert expert = expertRepository.findByEmail(email);

        System.out.println("El email es: " + email);

        if(expert == null) log.error("Expert not found with email: {}", email);
        
        return expert;
    }

    
    /** 
     * Esta función obtiene un experto de la base de datos, a partir del token de reset de contraseña
     * @param token
     * @return Expert
     */
    @Override
    public Expert getExpertByResetPasswordToken(String token) {
        return expertRepository.findByresetPasswordToken(token);
    }

    
    /** 
     * Esta función obtiene un experto de la base de datos, a partir del token de activación de cuenta
     * @param token
     * @return Expert
     */
    @Override
    public Expert getExpertByActivateAccountToken(String token) {
        return expertRepository.findByactivateAccountToken(token);
    }

    
    /** 
     * Esta función actualiza la contraseña de un experto en la base de datos
     * @param expert
     * @param newPassword
     */
    @Override
    public void updatePassword(Expert expert, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        
        expert.setPassword(encodedPassword);
        expert.setResetPasswordToken(null);

        expertRepository.save(expert);
    }

    
    /** 
     * Esta función actualiza el token de reset de contraseña de un experto en la base de datos
     * @param token
     * @param email
     * @throws UserNotFoundException
     */
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

    
    /** 
     * Esta función mapea un formulario de creación de experto a un experto 
     * @param expertForm
     * @return Expert
     */
    @Override
    public Expert mapExpertFormToExpert(ExpertCreationForm expertForm) {
        Expert expert = new Expert();
        
        Specialization specialization = specializationRepository.findByname(expertForm.getSpecialization());

        expert.setName(expertForm.getName());
        expert.setUsername(expertForm.getUsername());
        expert.setEmail(expertForm.getEmail());
        expert.setSurname(expertForm.getSurname());
        expert.setPassword(expertForm.getPassword());
        expert.setSpecialization(specialization);

        return expert;
    }

    
    /** 
     * Esta función mira si un experto existe en la base de datos
     * @param username
     * @return boolean
     */
    @Override
    public boolean checkExpertExistent(String username) {
        return expertRepository.existsByUsername(username);
    }

    
    /** 
     * Esta función añade a un usuario su respectivo token de activación de cuenta
     * @param username
     * @param token
     */
    @Override
    public void addActivationToken(String username, String token) {
        Expert expert = expertRepository.findByUsername(username);

        expert.setActivateAccountToken(token);
        expert.setAccountEnabled(false);

        expert = expertRepository.save(expert);        
    }

    
    /** 
     * Esta función habilita una cuenta de un experto
     * @param username
     * @param b
     */
    @Override
    public void setAccountEnabled(String username, boolean b) {
        Expert expert = expertRepository.findByUsername(username);

        expert.setAccountEnabled(b);
        expert.setActivateAccountToken(null);

        expertRepository.save(expert);
    }

}
