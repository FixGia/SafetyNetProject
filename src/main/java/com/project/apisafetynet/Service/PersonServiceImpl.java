package com.project.apisafetynet.Service;


import com.project.apisafetynet.Dao.LoadJsonFile;
import com.project.apisafetynet.Repository.PersonRepository;
import com.project.apisafetynet.model.Persons;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    final PersonRepository personRepository;
    final LoadJsonFile loadJsonfile;

    public PersonServiceImpl(PersonRepository personRepository, LoadJsonFile loadJsonfile) {
        this.personRepository = personRepository;
        this.loadJsonfile = loadJsonfile;
    }

    @Override
    public <S extends Persons> S save(S s) {
        return null;
    }

    @Override
    public <S extends Persons> Iterable<S> saveAll(Iterable<S> iterable) {
        return personRepository.saveAll(iterable);
    }

    @Override
    public Optional<Persons> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Persons> findAll() {
        return null;
    }

    @Override
    public Iterable<Persons> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Persons persons) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> iterable) {

    }

    @Override
    public void deleteAll(Iterable<? extends Persons> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}