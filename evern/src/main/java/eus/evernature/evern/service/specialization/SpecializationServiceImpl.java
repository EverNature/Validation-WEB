package eus.evernature.evern.service.specialization;

import java.util.ArrayList;
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


    @Override
    public List<Specialization> getAllSpecializations() {
        List<Specialization> specializations = specializationRepository.findAll();

        if(specializations.equals(null)) {
            log.error("Specializations not found in database");
            return new ArrayList<>();
        } else {
            log.info("Getting all specializations from database");
            return specializations;
        }
    }
    
}
