package com.oliveira25lucas.playroster.service;

import com.oliveira25lucas.playroster.model.Sport;
import com.oliveira25lucas.playroster.repository.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SportService {

    @Autowired
    private SportRepository sportRepository;

    public Sport findById(Long id) {
        Optional<Sport> sportOptional = sportRepository.findById(id);
        return sportOptional.orElseThrow(() -> new RuntimeException("Sport not found. ID: " + id));
    }

    public Sport findByName(String sportName) {
        Optional<Sport> sportOptional = sportRepository.findByName(sportName);
        return sportOptional.orElseThrow(() -> new RuntimeException("Sport: " + sportName + " was not found."));
    }

    public List<Sport> findAll() {
        return sportRepository.findAll();
    }

    @Transactional
    public Sport create(Sport sport) {
        sport.setId(null);
    return sportRepository.save(sport);
    }

    @Transactional
    public Sport update(Sport sport) {
        Sport sportUpd = findById(sport.getId());
        sportUpd.setName(sport.getName());
        sportUpd.setDescription(sport.getDescription());
        return sportRepository.save(sportUpd);
    }

    @Transactional
    public void deleteById(Long id) {
        findById(id);
        try{
            sportRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("It was not possible to delete Sport with ID: " + id);
        }
    }
}
