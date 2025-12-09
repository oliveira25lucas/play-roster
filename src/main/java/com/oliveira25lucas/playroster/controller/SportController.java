package com.oliveira25lucas.playroster.controller;

import com.oliveira25lucas.playroster.model.Sport;
import com.oliveira25lucas.playroster.service.SportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sport")
@Validated
@RequiredArgsConstructor
public class SportController {

    private SportService sportService;

    @GetMapping
    public ResponseEntity<List<Sport>> findAll() {
        List<Sport> sports = sportService.findAll();
        return ResponseEntity.ok(sports);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sport> findById(@PathVariable Long id){
        Sport sport = sportService.findById(id);
        return ResponseEntity.ok(sport);
    }

    @PostMapping
    public ResponseEntity<Sport> create(@Valid @RequestBody Sport sport) {
        Sport created = sportService.create(sport);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Sport> update(@PathVariable Long id, @Valid @RequestBody Sport sport) {
        sport.setId(id);
        Sport updated = sportService.update(sport);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        sportService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
