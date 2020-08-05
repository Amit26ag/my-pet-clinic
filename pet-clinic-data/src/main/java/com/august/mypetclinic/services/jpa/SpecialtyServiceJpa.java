package com.august.mypetclinic.services.jpa;

import com.august.mypetclinic.model.Specialty;
import com.august.mypetclinic.repositories.SpecialtyRepository;
import com.august.mypetclinic.services.SpecialtyService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("jpa")
public class SpecialtyServiceJpa implements SpecialtyService {

    private final SpecialtyRepository specialtyRepository;

    public SpecialtyServiceJpa(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    @Override
    public Specialty save(Specialty specialty) {
        return specialtyRepository.save(specialty);
    }

    @Override
    public Specialty findById(Long id) {
        return specialtyRepository.findById(id).orElse(null);
    }

    @Override
    public Set<Specialty> findAll() {
        Set<Specialty> specialties = new HashSet<>();
        specialtyRepository.findAll().forEach(specialties::add);
        return specialties;
    }

    @Override
    public void deleteById(Long id) {
        specialtyRepository.deleteById(id);
    }

    @Override
    public void delete(Specialty specialty) {
        specialtyRepository.delete(specialty);
    }
}
