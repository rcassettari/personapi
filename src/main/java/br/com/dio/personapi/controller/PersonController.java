package br.com.dio.personapi.controller;

import br.com.dio.personapi.dto.MessageResponseDTO;
import br.com.dio.personapi.entity.Person;
import br.com.dio.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/people")
public class PersonController {

    private PersonRepository personRepository;

    public PersonController( @Autowired PersonRepository personRepository )
    {
        this.personRepository = personRepository;
    }

    @PostMapping
    private MessageResponseDTO createPerson(@RequestBody Person person)
    {
        Person savedPerson = personRepository.save(person);
        return MessageResponseDTO
                .builder()
                .message("Created person with ID:" + savedPerson.getId())
                .build();
    }
}
