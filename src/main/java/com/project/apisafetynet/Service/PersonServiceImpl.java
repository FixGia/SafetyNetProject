package com.project.apisafetynet.Service;


import com.project.apisafetynet.Repository.PersonRepository;
import com.project.apisafetynet.model.Persons;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Data
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonRepository personRepository;

    @Override
    public <S extends Persons> S save(S s) {
        return null;
    }

    @Override
    public <S extends Persons> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
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
