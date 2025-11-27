package com.example.demo.controllers;

import com.example.demo.dtos.PersonDetailsDTO;
import com.example.demo.entities.Person;
import com.example.demo.services.PersonService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/person")
@Validated
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PersonDetailsDTO>> getPeople() {
        return ResponseEntity.ok(personService.findPersons());
    }

    @PostMapping("/add")
    public ResponseEntity<Void> create(@Valid @RequestBody Person person) {
        UUID id = personService.insert(person);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build(); // 201 + Location header
    }

    @GetMapping("get/{id}")
    public ResponseEntity<PersonDetailsDTO> getPerson(@PathVariable UUID id) {
        return ResponseEntity.ok(personService.findPersonById(id));
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable UUID id){
        personService.delete(id);
    }

    @PatchMapping("update/{id}")
    public Person update(@PathVariable UUID id, @RequestBody PersonDetailsDTO person){
        return personService.updatePerson(id, person);
    }

    @DeleteMapping("deleteAll")
    public void deleteAll(){
        personService.deleteAll();
    }
}
