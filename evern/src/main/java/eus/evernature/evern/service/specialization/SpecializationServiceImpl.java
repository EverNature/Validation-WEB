package eus.evernature.evern.service.specialization;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.evernature.evern.models.Specialization;
import eus.evernature.evern.repository.SpecializationRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@NoArgsConstructor
@Transactional
@Slf4j
public class SpecializationServiceImpl implements SpecializationService{

    @Autowired
    SpecializationRepository specializationRepository;


    
    /** 
     * Esta función obtiene todas las especializaciones de la base de datos
     * @return List<Specialization>
     */
    @Override
    public List<Specialization> getAllSpecializations() {
        List<Specialization> specializations = specializationRepository.findAll();

        if(specializations.isEmpty()) {
            log.info("There are no specializations in the database");
        } else {
            log.info("Getting all specializations from database");
        }
        return specializations;
    }
    
}
