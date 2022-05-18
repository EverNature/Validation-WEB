package eus.evernature.evern.service.animal;

import java.util.List;

import eus.evernature.evern.models.Animal;

public interface AnimalService {
    Animal saveAnimal(Animal animal);
    Animal getAnimal(String className);
    List<Animal> getAnimals();
}
