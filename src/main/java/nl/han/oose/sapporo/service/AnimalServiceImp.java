package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.AllAnimalDTO;
import nl.han.oose.sapporo.persistence.IAnimalDAO;

import javax.enterprise.inject.Default;
import javax.inject.Inject;

@Default
public class AnimalServiceImp implements IAnimalService {
    private IAnimalDAO AnimalDAO;

    @Inject
    public void setAnimalDAO(IAnimalDAO AnimalDAO) {
        this.AnimalDAO = AnimalDAO;
    }

    @Override
    public AllAnimalDTO getAllAnimals() {
        return AnimalDAO.getAllAnimals();
    }
}
