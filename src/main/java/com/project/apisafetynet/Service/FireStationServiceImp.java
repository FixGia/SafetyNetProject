package com.project.apisafetynet.Service;

import com.project.apisafetynet.Repository.FireStationRepository;
import com.project.apisafetynet.model.Firestation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
public class FireStationServiceImp implements FireStationService {

    final
    FireStationRepository fireStationRepository;

    public FireStationServiceImp(FireStationRepository fireStationRepository) {
        this.fireStationRepository = fireStationRepository;
    }

    @Override
    public List<Firestation> findAll() {
        return null;
    }

    @Override
    public List<Firestation> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Firestation> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Firestation> findAllById(Iterable<Long> iterable) {
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
    public void delete(Firestation firestation) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> iterable) {

    }

    @Override
    public void deleteAll(Iterable<? extends Firestation> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Firestation> S save(S s) {
        return null;
    }

    @Override
    public <S extends Firestation> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Firestation> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Firestation> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public <S extends Firestation> List<S> saveAllAndFlush(Iterable<S> iterable) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Firestation> iterable) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Firestation getOne(Long aLong) {
        return null;
    }

    @Override
    public Firestation getById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Firestation> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Firestation> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Firestation> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Firestation> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Firestation> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Firestation> boolean exists(Example<S> example) {
        return false;
    }
}