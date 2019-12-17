package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.AllAnimalDTO;
import nl.han.oose.sapporo.persistence.IAnimalDAO;

import javax.enterprise.inject.Default;
import javax.inject.Inject;

@Default
public class AnimalServiceImp implements IAnimalService {
    private IAnimalDAO animalDAO;

    @Inject
    public void setAnimalDAO(IAnimalDAO animalDAO) {
        this.animalDAO = animalDAO;
    }

    @Override
    public AllAnimalDTO getAllAnimals() {
        return animalDAO.getAllAnimals();
    }
}
