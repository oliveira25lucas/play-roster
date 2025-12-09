package com.oliveira25lucas.playroster.service;

import com.oliveira25lucas.playroster.exception.BadRequestException;
import com.oliveira25lucas.playroster.exception.ResourceNotFoundException;
import com.oliveira25lucas.playroster.model.Sport;
import com.oliveira25lucas.playroster.repository.SportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SportService {

    private final SportRepository sportRepository;

    public Sport findById(Long id) {
        return sportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sport not found. ID: " + id));
    }

    public Sport findByName(String sportName) {
        return sportRepository.findByName(sportName)
                .orElseThrow(() -> new ResourceNotFoundException("Sport not found. Name: " + sportName));
    }

    public List<Sport> findAll() {
        return sportRepository.findAll();
    }

    @Transactional
    public Sport create(Sport sport) {
        sport.setId(null);
        validateBusinessRulesOnCreate(sport);
        return sportRepository.save(sport);
    }

    @Transactional
    public Sport update(Sport sport) {
        Sport existing = findById(sport.getId());

        existing.setName(sport.getName());
        existing.setDescription(sport.getDescription());
        existing.setMinPlayers(sport.getMinPlayers());
        existing.setMaxPlayers(sport.getMaxPlayers());
        existing.setAllowDraw(sport.getAllowDraw());

        validateBusinessRulesOnUpdate(existing);
        return sportRepository.save(existing);
    }

    @Transactional
    public void deleteById(Long id) {
        Sport existing = findById(id);

        try {
            sportRepository.deleteById(existing.getId());
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("It was not possible to delete Sport with ID: " + id +
                    ". There are other records depending on this sport.");
        }
    }


    private void validateBusinessRulesOnCreate(Sport sport) {
        checkQuantityPlayers(sport);
        checkDuplicateNameOnCreate(sport);
    }

    private void validateBusinessRulesOnUpdate(Sport sport) {
        checkQuantityPlayers(sport);
        checkDuplicateNameOnUpdate(sport);
    }

    private void checkQuantityPlayers(Sport sport) {
        if (sport.getMinPlayers() == null || sport.getMaxPlayers() == null) {
            throw new BadRequestException("Min and max players must be informed");
        }

        if (sport.getMinPlayers() > sport.getMaxPlayers()) {
            throw new BadRequestException("Min players can't be greater than max players");
        }
    }

    private void checkDuplicateNameOnCreate(Sport sport) {
        if (sportRepository.existsByNameIgnoreCase(sport.getName())) {
            throw new BadRequestException("There is already a sport with name: " + sport.getName());
        }
    }

    private void checkDuplicateNameOnUpdate(Sport sport) {
        sportRepository.findByName(sport.getName())
                .ifPresent(found -> {
                    if (!found.getId().equals(sport.getId())) {
                        throw new BadRequestException("There is already a sport with name: " + sport.getName());
                    }
                });
    }
}
