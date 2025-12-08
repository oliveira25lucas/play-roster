package com.oliveira25lucas.playroster.controller;

import com.oliveira25lucas.playroster.model.Sport;
import com.oliveira25lucas.playroster.service.SportService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sport")
@Validated
public class SportController {

    @Autowired
    private SportService sportService;

    @GetMapping("/{id}")
    public ResponseEntity<Sport > findById(@PathVariable Long id){
        Sport sport = sportService.findById(id);
        return ResponseEntity.ok().body(sport);
    }

    @PostMapping
    public ResponseEntity<Sport> create(@Valid @RequestBody Sport sport){
        sportService.create(sport);
        return ResponseEntity.status(HttpStatus.CREATED).body(sportService.create(sport));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Sport> update(@PathVariable Long id, @Valid @RequestBody Sport sport){
        sport.setId(id);
        sportService.update(sport);
        return ResponseEntity.ok().body(sport);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        sportService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
