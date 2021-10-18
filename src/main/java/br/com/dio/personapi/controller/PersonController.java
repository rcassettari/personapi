package br.com.dio.personapi.controller;

import br.com.dio.personapi.dto.MessageResponseDTO;
import br.com.dio.personapi.entity.Person;
import br.com.dio.personapi.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/people")
public class PersonController {

    private PersonService personService;

    public PersonController(@Autowired PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    @ResponseStatus( HttpStatus.CREATED )
    private MessageResponseDTO createPerson(@RequestBody Person person) {
        return personService.createPerson(person);
    }
}
