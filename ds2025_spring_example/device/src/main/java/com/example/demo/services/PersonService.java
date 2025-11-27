package com.example.demo.services;

import com.example.demo.entities.Person;
import com.example.demo.handlers.exceptions.model.ResourceNotFoundException;
import com.example.demo.repositories.PersonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public HttpStatus addPerson(UUID id){
        try {
            Person person = new Person(id);
            this.personRepository.save(person);
            return HttpStatus.OK;
        } catch (Exception e) {
            throw new RuntimeException("Error while adding person in Device", e);
        }
    }

    public HttpStatus deletePerson(UUID id){
        try{
            this.personRepository.deleteById(id);
            return HttpStatus.OK;
        }
        catch(Exception e)
        {
            throw new RuntimeException("Error while deleting person in Device", e);
        }
    }

    public Person findPersonById(UUID id) {
            return this.personRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Owner not found!"));
    }

    public List<Person> getAll(){
        return this.personRepository.findAll();
    }
}
