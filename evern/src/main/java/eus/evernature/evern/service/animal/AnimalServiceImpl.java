package eus.evernature.evern.service.animal;

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
    
    
    /** 
     * Esta función guarda un animal en la base de datos
     * @param animal El animal a guardar en la base de datos
     * @return Animal El animal guardado
     */
    @Override
    public Animal saveAnimal(Animal animal) {
        return animalRepository.save(animal);
    }

    
    /** 
     * Esta función obtiene un animal teniendo el nombre de la base de datos
     * @param className El nombre de la clase del animal
     * @return Animal El animal buscado
     */
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

    /** 
     * Esta función obtiene todos los animales de la base de datos
     * @return List<Animal> Una lista con todos los animales de la base de datos
     */
    @Override
    public List<Animal> getAnimals() {
        return animalRepository.findAll();
    }
    
}
