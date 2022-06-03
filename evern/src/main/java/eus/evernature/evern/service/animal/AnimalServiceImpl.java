package eus.evernature.evern.service.animal;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eus.evernature.evern.models.Animal;
import eus.evernature.evern.repository.AnimalRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@NoArgsConstructor
@Transactional
@Slf4j
public class AnimalServiceImpl implements AnimalService {

    @Autowired
    AnimalRepository animalRepository;
    
    @Override
    public Animal saveAnimal(Animal animal) {
        return animalRepository.save(animal);
    }

    @Override
    public Animal getAnimal(String className)  {

        Animal animal = animalRepository.findByName(className);

        if(animal == null) {
            log.error("Animal not found in the database instance");
            return animal;
        }
        
        log.info("Animal found in database: {}", animal.getName());
        
        return animal;
    }

    @Override
    public List<Animal> getAnimals() {
        return animalRepository.findAll();
    }
    
}
